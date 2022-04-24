package com.ewolutionary.alg.views;

import com.ewolutionary.alg.function.Functions;
import com.ewolutionary.alg.impl.Configuration;
import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Solution;
import com.ewolutionary.alg.impl.Solver;
import com.ewolutionary.alg.impl.crossers.CrosserOption;
import com.ewolutionary.alg.impl.mutators.MutatorOption;
import com.ewolutionary.alg.impl.selectors.SelectorOption;
import com.ewolutionary.alg.impl.selectors.configuration.BestSelectorConfiguration;
import com.ewolutionary.alg.impl.selectors.configuration.RouletteSelectorConfiguration;
import com.ewolutionary.alg.impl.selectors.configuration.SelectorConfiguration;
import com.ewolutionary.alg.impl.selectors.configuration.TournamentSelectorConfiguration;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.util.Map;

@PageTitle("")
@Route(value = "")
public class View extends HorizontalLayout {

    private final ComboBox<CrosserOption> crossers = new ComboBox<>("Crosser", CrosserOption.values());
    private final ComboBox<MutatorOption> mutators = new ComboBox<>("Mutator", MutatorOption.values());
    private final ComboBox<SelectorOption> selectors = new ComboBox<>("Selector", SelectorOption.values());
    private final ComboBox<String> functionMinMax = new ComboBox<>("Function value", "MIN", "MAX");
    private final Checkbox isEliteStrategy = new Checkbox("Elite strategy");
    private final ComboBox<String> buildInFunction = new ComboBox<>("Build in function");
    private final Map<String, Integer> internalFunctions = Map.of("x0^2+x1", 2, "2x0^2+5", 1, "BealFunction", 2, "BraninFunction", 2, "EasomFunction", 2);
    private final Checkbox isCustom = new Checkbox("Custom");
    private final TextField customFunction = new TextField("Custom function");
    private final Button solveButton = new Button("Solve");
    //4 config
    private final IntegerField sizeOfPopulation = new IntegerField("Size of population");
    private final IntegerField start = new IntegerField("Start");
    private final IntegerField stop = new IntegerField("Stop");
    private final IntegerField percentOfBestToNextCentury = new IntegerField("% of best to pass");
    private final IntegerField maxIterations = new IntegerField("Maximum iterations");
    private final NumberField crossingProbability = new NumberField("Crossing probability");
    private final NumberField mutationProbability = new NumberField("Mutation probability");
    private final IntegerField numOfVariables = new IntegerField("Number of variables");

    private Solver solver;
    private Solution solution;
    private Configuration configuration;
    private SelectorConfiguration selectorConfiguration;

    public View() {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout comboBoxesLayout = new HorizontalLayout(crossers, mutators, selectors);
        comboBoxesLayout.setPadding(true);
        comboBoxesLayout.setVerticalComponentAlignment(Alignment.CENTER);

        HorizontalLayout buttons = new HorizontalLayout(isEliteStrategy);
        buttons.setPadding(true);

        selectors.addValueChangeListener(event -> {
            SelectorOption option = event.getValue();
            Button save = new Button("Save");
            Button cancel = new Button("Cancel");
            Dialog dialog = new Dialog();
            dialog.setCloseOnOutsideClick(false);
            cancel.addClickListener(e -> {
                if (selectorConfiguration != null) {
                    dialog.close();
                } else {
                    Notification.show("Selector configuration required!");
                }
            });
            NumberField bestPercentage = new NumberField("Best percentage");
            bestPercentage.setPlaceholder("0.XXX");
            bestPercentage.setMax(1.0);
            bestPercentage.setMin(0);
            HorizontalLayout saveAndCancel = new HorizontalLayout(save, cancel);
            VerticalLayout dialogLayout = new VerticalLayout(bestPercentage, saveAndCancel);
            switch (option) {
                case BEST:
                    dialog.add(dialogLayout);
                    save.addClickListener(e -> {
                        selectorConfiguration = new BestSelectorConfiguration(bestPercentage.getValue());
                        dialog.close();
                    });

                    dialog.open();
                    break;
                case ROULETTE:
                    dialog.add(dialogLayout);
                    save.addClickListener(e -> {
                                selectorConfiguration = new RouletteSelectorConfiguration(bestPercentage.getValue());
                                dialog.close();
                            }
                    );
                    dialog.open();
                    break;
                case TOURNAMENT:
                    IntegerField entitiesInTournament = new IntegerField("Entities in Tournament");
                    IntegerField amountOfTournaments = new IntegerField("Amount of Tournaments");
                    save.addClickListener(e -> {
                                selectorConfiguration = new TournamentSelectorConfiguration(
                                        entitiesInTournament.getValue(), amountOfTournaments.getValue());
                                dialog.close();
                            }
                    );
                    dialog.add(new HorizontalLayout(entitiesInTournament, amountOfTournaments), saveAndCancel);
                    dialog.open();
            }

        });

        customFunction.setEnabled(false);

        VerticalLayout functionLayout = new VerticalLayout(
                new VerticalLayout(new HorizontalLayout(buildInFunction, customFunction, numOfVariables, functionMinMax), isCustom)
        );
        functionLayout.setPadding(true);
        functionLayout.addClassName(".gap-m");
        functionLayout.setSpacing(true);
        buildInFunction.setWidthFull();
        buildInFunction.setItems(internalFunctions.keySet());
        numOfVariables.setWidthFull();
        isCustom.setWidthFull();
        functionMinMax.setWidthFull();
        customFunction.setWidthFull();
        customFunction.setEnabled(false);
        numOfVariables.setEnabled(false);

        isCustom.addClickListener(e -> {
            if(isCustom.getValue()) {
                customFunction.setEnabled(true);
                numOfVariables.setEnabled(true);
                buildInFunction.setEnabled(false);
            } else {
                customFunction.setEnabled(false);
                numOfVariables.setEnabled(false);
                buildInFunction.setEnabled(true);
            }
        });

        Details functionConfiguration = new Details("Function configuration", functionLayout);

        VerticalLayout configurationLayout = new VerticalLayout(
                new HorizontalLayout(start, stop, sizeOfPopulation),
                new HorizontalLayout(maxIterations, percentOfBestToNextCentury),
                new HorizontalLayout(crossingProbability, mutationProbability));
        configurationLayout.setPadding(true);
        configurationLayout.addClassName(".gap-m");
        configurationLayout.setSpacing(true);
        percentOfBestToNextCentury.setWidthFull();
        sizeOfPopulation.setWidthFull();
        start.setWidthFull();
        stop.setWidthFull();
        percentOfBestToNextCentury.setWidthFull();
        maxIterations.setWidthFull();
        crossingProbability.setWidthFull();
        mutationProbability.setWidthFull();

        mutationProbability.setMin(0);
        mutationProbability.setMax(1);
        mutationProbability.setPlaceholder("0.XXX");

        crossingProbability.setMin(0);
        crossingProbability.setMax(1);
        crossingProbability.setPlaceholder("0.XXX");

        Details configurationDetails = new Details("Additional configuration", configurationLayout);

        percentOfBestToNextCentury.setEnabled(false);
        isEliteStrategy.addClickListener(click -> {
            percentOfBestToNextCentury.setEnabled(isEliteStrategy.getValue());
        });

        solveButton.addClickListener(event -> {
            if (requiredFieldsEmpty()) {
                Notification.show("Fill out all the required fields");
            } else {
                int numberOfX = isCustom.getValue()?numOfVariables.getValue():internalFunctions.get(buildInFunction.getValue());

                Configuration.ConfigurationBuilder builder = Configuration.builder()
                        .sizeOfPopulation(sizeOfPopulation.getValue())
                        .start(start.getValue())
                        .stop(stop.getValue())
                        .maxIterations(maxIterations.getValue())
                        .selectorConfiguration(selectorConfiguration)
                        .crossingProbability(crossingProbability.getValue())
                        .isEliteStrategy(isEliteStrategy.getValue())
                        .mutationProbability(mutationProbability.getValue())
                        .xVariableCount(numberOfX);
                if (!percentOfBestToNextCentury.isEmpty()) {
                    builder.percentOfBestToNextCentury(percentOfBestToNextCentury.getValue());
                }
                configuration = builder.build();
                solver = new Solver(mutators.getValue(), crossers.getValue(), selectors.getValue(),
                        getFunction(), configuration);
                Functions.MINIMUM = functionMinMax.getValue().equals("MIN");
                try {
                    solution = solver.solve();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                VerticalLayout resultLayout = new VerticalLayout(
                        new Html("<p>Time: " + solution.getTimeMilis() + "ms" +
                                "<br>Number of iterations: " + solution.getNumberOfIterations() +
                                "<br>Found solution: " + solution.getBestEntity().toString().replace("value", "<br>value") + "</p>")
                );

                if (configuration.getXVariableCount() == 2) {
                    Chart chart = genChartTwoVariables();
                    resultLayout.add(chart);
                } else if (configuration.getXVariableCount() == 1) {
                    //TODO NOT WORKING 4 ONE VARIABLE
                    solution.getBestEntityInEachIteration().forEach(entity -> entity.getChromosomesValues().add(entity.getFitness()));
                    //Chart chart = genChartTwoVariables();
                    Chart chart = genChartOneVariable();
                    resultLayout.add(chart);
                }

                resultLayout.add(new Text("To exit press ESC"));

                Dialog result = new Dialog(resultLayout);
                result.setCloseOnOutsideClick(false);
                result.open();
            }
        });

        HorizontalLayout solveButtonLayout = new HorizontalLayout(solveButton);
        solveButtonLayout.setPadding(true);
        layout.add(comboBoxesLayout, functionConfiguration, configurationDetails, buttons, solveButtonLayout);
        add(layout);
    }

    private String getFunction() {
        if(isCustom.getValue() && !customFunction.isEmpty()) {
            return customFunction.getValue();
        }
        if(!isCustom.getValue() && !buildInFunction.isEmpty()) {
            return buildInFunction.getValue();
        }
        throw new IllegalArgumentException("Function cannot be null!");
    }

    private boolean requiredFieldsEmpty() {
        boolean isFunctionEmpty = (isCustom.getValue() && customFunction.isEmpty()) || (!isCustom.getValue() && buildInFunction.isEmpty());
        return crossers.isEmpty() || mutators.isEmpty() || selectors.isEmpty()
                || start.isEmpty() || stop.isEmpty() || (numOfVariables.isEmpty() && isCustom.getValue())
                || sizeOfPopulation.isEmpty() || maxIterations.isEmpty()
                || crossingProbability.isEmpty() || mutationProbability.isEmpty() || functionMinMax.isEmpty() || isFunctionEmpty;
    }

    private Chart genChartTwoVariables() {
        Chart chart = new Chart(ChartType.SCATTER);
        com.vaadin.flow.component.charts.model.Configuration conf = chart.getConfiguration();
        conf.setTitle("Best solutions in each iteration");
        conf.getxAxis().setTitle("X0");
        conf.getyAxis().setTitle("X1");
        conf.getxAxis().setStartOnTick(true);
        conf.getxAxis().setShowLastLabel(true);
        conf.getxAxis().setEndOnTick(true);

        conf.getxAxis().setMax(configuration.getStop());
        conf.getxAxis().setMin(configuration.getStart());
        conf.getyAxis().setMax(configuration.getStop());
        conf.getyAxis().setMin(configuration.getStart());

        DataSeries series = new DataSeries();
        PlotOptionsScatter options = new PlotOptionsScatter();
        SeriesTooltip seriesTooltip = new SeriesTooltip();
        seriesTooltip.setFollowPointer(true);
        seriesTooltip.setPointFormat("{point.name}");
        options.setTooltip(seriesTooltip);
        options.setAllowPointSelect(true);
        series.setPlotOptions(options);

        int iter = 0;
        for (Entity e : solution.getBestEntityInEachIteration()) {
            DataSeriesItem point = new DataSeriesItem(e.getChromosomesValues().get(0), e.getChromosomesValues().get(1));

            point.setName("Iteration " + iter + " Value: " + e.getFitness());

            series.add(point);
            iter++;
        }
        conf.addSeries(series);
        Tooltip tooltip = new Tooltip(true);
        tooltip.setPointFormat("{point.name}");
        conf.setTooltip(tooltip);

        return chart;
    }

    private Chart genChartOneVariable() {
        Chart chart = new Chart(ChartType.SCATTER);
        com.vaadin.flow.component.charts.model.Configuration conf = chart.getConfiguration();
        conf.setTitle("Best solutions in each iteration");
        conf.getxAxis().setTitle("X");
        conf.getyAxis().setTitle("Y");
        conf.getxAxis().setStartOnTick(true);
        conf.getxAxis().setShowLastLabel(true);
        conf.getxAxis().setEndOnTick(true);

        conf.getxAxis().setMax(configuration.getStop());
        conf.getxAxis().setMin(configuration.getStart());
        conf.getyAxis().setMax(configuration.getStop());
        conf.getyAxis().setMin(configuration.getStart());

        DataSeries series = new DataSeries();
        PlotOptionsScatter options = new PlotOptionsScatter();
        SeriesTooltip seriesTooltip = new SeriesTooltip();
        seriesTooltip.setFollowPointer(true);
        seriesTooltip.setPointFormat("{point.name}");
        options.setTooltip(seriesTooltip);
        options.setAllowPointSelect(true);
        series.setPlotOptions(options);

        int iter = 0;
        for (Entity e : solution.getBestEntityInEachIteration()) {
            DataSeriesItem point = new DataSeriesItem(e.getChromosomesValues().get(0), e.getFitness());

            point.setName("Iteration " + iter + " Value: " + e.getFitness());

            series.add(point);
            iter++;
        }
        conf.addSeries(series);
        Tooltip tooltip = new Tooltip(true);
        tooltip.setPointFormat("{point.name}");
        conf.setTooltip(tooltip);

        return chart;
    }

}
