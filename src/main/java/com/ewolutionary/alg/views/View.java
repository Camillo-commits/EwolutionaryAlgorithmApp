package com.ewolutionary.alg.views;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("")
@Route(value = "")
public class View extends HorizontalLayout {

    private final ComboBox<CrosserOption> crossers = new ComboBox<>("Crosser", CrosserOption.values());
    private final ComboBox<MutatorOption> mutators = new ComboBox<>("Mutator", MutatorOption.values());
    private final ComboBox<SelectorOption> selectors = new ComboBox<>("Selector", SelectorOption.values());
    private final Checkbox isInverter = new Checkbox("Inverter");
    private final Checkbox isEliteStrategy = new Checkbox("Elite strategy");
    private final ComboBox<String> buildInFunction = new ComboBox<>("Build in function", "x0^2+x1", "2x0^2+5");
    private final Button solveButton = new Button("Solve");
    //4 config
    private final IntegerField sizeOfPopulation = new IntegerField("Size of population");
    private final IntegerField precision = new IntegerField("Precision");
    private final IntegerField start = new IntegerField("Start");
    private final IntegerField stop = new IntegerField("Stop");
    private final IntegerField percentOfBestToNextCentury = new IntegerField("% of best to pass");
    private final IntegerField maxIterations = new IntegerField("Maximum iterations");
    private final NumberField crossingProbability = new NumberField("Crossing probability");
    private final NumberField mutationProbability = new NumberField("Mutation probability");
    private final NumberField inversionProbability = new NumberField("Inversion probability");
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

        HorizontalLayout buttons = new HorizontalLayout(isEliteStrategy, isInverter);
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

        VerticalLayout functionLayout = new VerticalLayout(
                new HorizontalLayout(buildInFunction, numOfVariables)
        );
        functionLayout.setPadding(true);
        functionLayout.addClassName(".gap-m");
        functionLayout.setSpacing(true);
        buildInFunction.setWidthFull();
        numOfVariables.setWidthFull();

        Details functionConfiguration = new Details("Function configuration", functionLayout);
        buildInFunction.setAllowCustomValue(true);

        VerticalLayout configurationLayout = new VerticalLayout(
                new HorizontalLayout(start, stop, sizeOfPopulation),
                new HorizontalLayout(precision, maxIterations, percentOfBestToNextCentury),
                new HorizontalLayout(crossingProbability, mutationProbability, inversionProbability));
        configurationLayout.setPadding(true);
        configurationLayout.addClassName(".gap-m");
        configurationLayout.setSpacing(true);
        percentOfBestToNextCentury.setWidthFull();
        sizeOfPopulation.setWidthFull();
        precision.setWidthFull();
        start.setWidthFull();
        stop.setWidthFull();
        percentOfBestToNextCentury.setWidthFull();
        maxIterations.setWidthFull();
        crossingProbability.setWidthFull();
        mutationProbability.setWidthFull();
        inversionProbability.setWidthFull();

        mutationProbability.setMin(0);
        mutationProbability.setMax(1);
        mutationProbability.setPlaceholder("0.XXX");

        crossingProbability.setMin(0);
        crossingProbability.setMax(1);
        crossingProbability.setPlaceholder("0.XXX");

        inversionProbability.setMin(0);
        inversionProbability.setMax(1);
        inversionProbability.setPlaceholder("0.XXX");

        Details configurationDetails = new Details("Additional configuration", configurationLayout);

        percentOfBestToNextCentury.setEnabled(false);
        isEliteStrategy.addClickListener(click -> {
            if (isEliteStrategy.getValue()) {
                percentOfBestToNextCentury.setEnabled(true);
            } else {
                percentOfBestToNextCentury.setEnabled(false);
            }
        });

        inversionProbability.setEnabled(false);
        isInverter.addClickListener(click -> {
            if (isInverter.getValue()) {
                inversionProbability.setEnabled(true);
            } else {
                inversionProbability.setEnabled(false);
            }
        });

        solveButton.addClickListener(event -> {
            if (requiredFieldsEmpty()) {
                Notification.show("Fill out all the required fields");
            } else {
                Configuration.ConfigurationBuilder builder = Configuration.builder()
                        .isInverter(isInverter.getValue())
                        .sizeOfPopulation(sizeOfPopulation.getValue())
                        .precision(precision.getValue())
                        .start(start.getValue())
                        .stop(stop.getValue())
                        .maxIterations(maxIterations.getValue())
                        .selectorConfiguration(selectorConfiguration)
                        .crossingProbability(crossingProbability.getValue())
                        .isEliteStrategy(isEliteStrategy.getValue())
                        .mutationProbability(mutationProbability.getValue())
                        .xVariableCount(numOfVariables.getValue());
                if (!percentOfBestToNextCentury.isEmpty()) {
                    builder.percentOfBestToNextCentury(percentOfBestToNextCentury.getValue());
                }
                if (!inversionProbability.isEmpty()) {
                    builder.inversionProbability(inversionProbability.getValue());
                }
                configuration = builder.build();
                solver = new Solver(mutators.getValue(), crossers.getValue(), selectors.getValue(),
                        buildInFunction.getValue(), configuration);

                solution = solver.solve();
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
                    solution.getBestEntityInEachIteration().forEach(entity -> entity.getValue().add(entity.getFitness()));
                    Chart chart = genChartTwoVariables();
                    //Chart chart = genChartOneVariable();
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

    private boolean requiredFieldsEmpty() {
        return crossers.isEmpty() || mutators.isEmpty() || selectors.isEmpty()
                || start.isEmpty() || stop.isEmpty() || numOfVariables.isEmpty()
                || sizeOfPopulation.isEmpty() || precision.isEmpty() || maxIterations.isEmpty()
                || crossingProbability.isEmpty() || mutationProbability.isEmpty() || buildInFunction.isEmpty();
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
            DataSeriesItem point = new DataSeriesItem(e.getValue().get(0), e.getValue().get(1));

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
            DataSeriesItem point = new DataSeriesItem(e.getValue().get(0), e.getFitness());

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

    public static void main(String[] args) {
        Configuration conf = new Configuration(10, 2, -10, 10, 20, 10, true, false, 0.25, 0.20, 0.25, 1, new RouletteSelectorConfiguration(0.20));
        Solver solver = new Solver(MutatorOption.ONE_POINT, CrosserOption.HOMOGENEOUS, SelectorOption.ROULETTE, "2x^2+5", conf);
        Solution e = solver.solve();
    }

}
