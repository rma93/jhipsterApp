package com.ructor.rmenaa.cucumber.stepdefs;

import com.ructor.rmenaa.Prueba3App;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = Prueba3App.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
