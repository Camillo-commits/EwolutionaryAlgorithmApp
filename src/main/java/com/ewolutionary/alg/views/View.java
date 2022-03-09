package com.ewolutionary.alg.views;

import com.ewolutionary.alg.impl.Solver;
import com.ewolutionary.alg.impl.crossers.CrosserOption;
import com.ewolutionary.alg.impl.mutators.MutatorOption;
import com.ewolutionary.alg.impl.selectors.SelectorOption;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("")
@Route(value = "")
public class View extends HorizontalLayout {

    private final ComboBox<CrosserOption> crossers = new ComboBox<>("Crosser", CrosserOption.values());
    private final ComboBox<MutatorOption> mutators = new ComboBox<>("Mutator", MutatorOption.values());
    private final ComboBox<SelectorOption> selectors = new ComboBox<>("Selector", SelectorOption.values());
    private final Checkbox isInverter = new Checkbox("Inverter");
    private final Button solveButton = new Button("Solve");
    private Solver solver;

    public View() {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout comboBoxesLayout = new HorizontalLayout(crossers, mutators, selectors);
        comboBoxesLayout.setPadding(true);
        comboBoxesLayout.setVerticalComponentAlignment(Alignment.CENTER);

        HorizontalLayout buttons = new HorizontalLayout(isInverter, solveButton);
        buttons.setPadding(true);

        solveButton.addClickListener(event -> {
           solver = new Solver(mutators.getValue(), crossers.getValue(), selectors.getValue(), isInverter.getValue());
        });

        layout.add(comboBoxesLayout, buttons);
        add(layout);
    }

}
