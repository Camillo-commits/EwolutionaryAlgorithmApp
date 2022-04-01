package com.ewolutionary.alg.views;

import com.ewolutionary.alg.impl.Configuration;
import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Solver;
import com.ewolutionary.alg.impl.crossers.CrosserOption;
import com.ewolutionary.alg.impl.mutators.MutatorOption;
import com.ewolutionary.alg.impl.selectors.SelectorOption;
import com.ewolutionary.alg.impl.selectors.configuration.RouletteSelectorConfiguration;
import com.ewolutionary.alg.impl.selectors.configuration.SelectorConfiguration;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("")
@Route(value = "")
public class View extends HorizontalLayout {

    private final ComboBox<CrosserOption> crossers = new ComboBox<>("Crosser", CrosserOption.values());
    private final ComboBox<MutatorOption> mutators = new ComboBox<>("Mutator", MutatorOption.values());
    private final ComboBox<SelectorOption> selectors = new ComboBox<>("Selector", SelectorOption.values());
    private final Checkbox isInverter = new Checkbox("Inverter");
    private final TextField function = new TextField("x1^2+x2");
    private final Button solveButton = new Button("Solve");
    private Solver solver;
    private Entity solution;

    public View() {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout comboBoxesLayout = new HorizontalLayout(crossers, mutators, selectors);
        comboBoxesLayout.setPadding(true);
        comboBoxesLayout.setVerticalComponentAlignment(Alignment.CENTER);

        HorizontalLayout buttons = new HorizontalLayout(isInverter, solveButton);
        buttons.setPadding(true);

        solveButton.addClickListener(event -> {
            //TODO - add population size option on page
            solver = new Solver(mutators.getValue(), crossers.getValue(), selectors.getValue(), function.getValue(), Configuration.
                    builder()
                    .isInverter(isInverter.getValue())
                    .build());
            solution = solver.solve();
        });

        layout.add(comboBoxesLayout, buttons);
        add(layout);
    }

    public static void main(String[] args) {
        Configuration conf = new Configuration(10, 2, -10, 10, 20, 10, true, false, 0.25, 0.20, 0.25, 1, new RouletteSelectorConfiguration(0.20));
        Solver solver = new Solver(MutatorOption.ONE_POINT, CrosserOption.HOMOGENEOUS, SelectorOption.ROULETTE, "2x^2+5", conf);
        Entity e = solver.solve();
    }

}
