public final class GraphBuilder {
  
  private int x = 20;
  private int y = 20;
  private int[] dataPoints;
  private GraphVisualizationMode visualizationMode;
  
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
  
  public GraphBuilder visualizationMode(GraphVisualizationMode visualizationMode) {
    this.visualizationMode = visualizationMode;
    return this;
  }
  
  public String build() {
   return null; 
  }
  
}
