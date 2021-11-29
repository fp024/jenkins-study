package io.jenkins.plugins.sample;

import hudson.model.Run;
import jenkins.model.RunAction2;

public class HelloWorldAction implements RunAction2 { // <== (1)
  private transient Run run; // <== (2)

  @Override
  public void onAttached(Run<?, ?> run) {
    this.run = run; // <== (3)
  }

  @Override
  public void onLoad(Run<?, ?> run) {
    this.run = run; // <== (4)
  }

  public Run getRun() {
    return run;
  }

  private String name;

  public HelloWorldAction(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String getIconFileName() {
    return "document.png"; // <== (1)
  }

  @Override
  public String getDisplayName() {
    return "Greeting"; // <== (2)
  }

  @Override
  public String getUrlName() {
    return "Greeting"; // <== (3)
  }
}
