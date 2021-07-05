package de.terrarier.randomutils.graph;

public final class GraphBuilder {
  
  private int x = 20;
  private int y = 20;
  private int[] dataPoints;
  private GraphDifferenceVisualizationMode visualizationMode;
  private boolean printNumberHelp;
  
  public GraphBuilder x(int x) {
    this.x = x;
    return this;
  }
  
  public GraphBuilder y(int y) {
    this.y = y;
    return this;
  }
  
  public GraphBuilder dataPoints(int[] dataPoints) {
    this.dataPoints = dataPoints;
    return this;
  }
  
  public GraphBuilder visualizationMode(GraphDifferenceVisualizationMode visualizationMode) {
    this.visualizationMode = visualizationMode;
    return this;
  }
  
  public GraphBuilder printNumberHelp(boolean printNumberHelp) {
    this.printNumberHelp = printNumberHelp;
    return this;
  }
  
  public String build() {
   return GraphUtil.buildScaledGraphVert(x, y, dataPoints, visualizationMode, printNumberHelp); 
  }
  
}
