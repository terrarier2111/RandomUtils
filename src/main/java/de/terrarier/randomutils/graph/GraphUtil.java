package de.terrarier.randomutils.graph;

public final class GraphUtil {

    private static final char BOX_FILLED_CHAR = '▇';
    private static final char BOX_HALF_FILLED_CHAR = '▃';
    private static final char BOX_EMPTY_CHAR = '░';
    private static final char EMPTY_CHAR = ' ';

    // example
    public static void main(String[] args) {
        final int[] input = new int[]{15, 13, 12, 12, 10, 9, 7, 4, 2, 0};
        // BubbleSort.sort(input); // used to reverse the input
        System.out.println(buildScaledGraphVert(20, 10, input, GraphDifferenceVisualizationMode.INCREASE, true));
        System.out.println(new GraphBuilder().x(20).y(10).dataPoints(input)
                           .visualizationMode(GraphDifferenceVisualizationMode.DECAY)
                           .printNumberHelp(true).build());
    }
    
     // Note: This method can only generate vertical graphs
    public static String buildScaledGraphVert(int x, int y, int[] dataPoints, GraphDifferenceVisualizationMode visualizationMode,
                                               boolean printNumberHelp) {
        final int[] peak = peak(dataPoints);
        y = Math.min(y, peak[0]);
        x = Math.min(x, dataPoints.length);
        final int columns = Math.max(dataPoints.length / x, 1);
        final char[][][] graph = new char[columns][][]; // columns(parts in which the graph was split) | x rows | y rows
        for (int i = 0; i < columns; i++) {
            graph[i] = new char[x][];
            for (int j = 0; j < x; j++) {
                graph[i][j] = new char[y];
            }
        }
        final double scale = Math.max((double) peak[0] / (double) y, 1);
        System.out.println("scale: " + scale);
        int lastYRows = (int) (dataPoints[0] * scale);
        for (int i = 0; i < dataPoints.length; i++) {
            final int row = i / x;
            final int subIndex = i % x;
            final int dataPoint = dataPoints[i];
            final int yRows = (int) (dataPoint / scale);
            final int diff = diff(lastYRows, i == dataPoints.length - 1 ? yRows : dataPoints[i + 1], yRows, visualizationMode);
            insertEntryToGraph(graph[row], subIndex, yRows, diff);
            //
            final boolean halfYRow = dataPoint - ((double) yRows * scale) >= scale / 2;
            if(visualizationMode == GraphDifferenceVisualizationMode.NONE && halfYRow) {
                graph[row][subIndex][yRows] = BOX_HALF_FILLED_CHAR;
            }
            //
            lastYRows = yRows;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < graph.length; i++) {
            final char[][] row = graph[i];
            final int length = row[peak[1]].length;
            final int longestHelp = printNumberHelp ? Double.toString(y * scale).length() : 0;
            for (int l = 0; l < length; l++) {
                if(printNumberHelp) {
                    final String currentHelp = Double.toString((y - l) * scale);
                    sb.append(currentHelp);
                    final int diff = longestHelp - currentHelp.length();
                    for(int k = 0; k < diff; k++) {
                        sb.append(EMPTY_CHAR);
                    }
                }
                for (int j = 0; j < row.length; j++) {
                    final char[] yRow = row[j];
                    sb.append(yRow[yRow.length - 1 - l]);
                }
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    // TODO: Fix that when you try to build a graph, the last entry from data points will be dropped for some reason
    // Note: This method can only generate horizontal, descending graphs
    public static String buildScaledGraphHoriz(int x, int y, int[] dataPoints) {
        final int columns = Math.max(dataPoints.length / x, 1);
        final char[][][] graph = new char[columns][][]; // columns | y lines | individual chars
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new char[Math.min(x, dataPoints.length)][];
            for (int j = 0; j < graph[i].length; j++) {
                graph[i][j] = new char[Math.min(y, dataPoints[i])];
            }
        }
        // System.out.println(graph[0][0].length);
        // System.out.println(graph[0].length);
        final int scale = Math.max(dataPoints[0] / y, 1);
        int lastYRows = dataPoints[0];
        for (int i = 0; i < dataPoints.length; i++) {
            final int row = i / x;
            final int subIndex = i % x;
            final int dataPoint = dataPoints[i];
            final int yRows = dataPoint / scale;
            final int diff = lastYRows - yRows;
            // System.out.println("diff: " + diff);
            insertEntryToGraph(graph[row], subIndex, yRows, diff);
            lastYRows = yRows;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < graph.length; i++) {
            final char[][] row = graph[i];
            for (int j = 0; j < row.length - 1; j++) {
                final char[] yRow = row[j];
                sb.append(yRow).append('\n');
            }
            if (i != graph.length - 1) {
                sb.append('\n')
                        .append('\n')
                        .append('\n');
            }
        }
        return sb.toString();
    }
    
    private static void insertEntryToGraph(char[][] graph, int offset, int yCharsFilled, int yCharsEmptied) {
        for (int i = graph[0].length - 1; i > -1; i--) {
            graph[offset][i] = (i >= yCharsFilled ? (i - yCharsFilled >= yCharsEmptied ? EMPTY_CHAR : BOX_EMPTY_CHAR) : BOX_FILLED_CHAR);
        }
    }

    private static int[] peak(int[] nums) {
        int biggestIdx = -1;
        int biggest = Integer.MIN_VALUE;
        for(int i = 0; i < nums.length; i++) {
            final int curr = nums[i];
            if(curr >= biggest) {
                biggest = curr;
                biggestIdx = i;
            }
        }
        return new int[]{biggest, biggestIdx};
    }

    private static int diff(int lastRows, int nextRows, int currentRows, GraphDifferenceVisualizationMode visualizationMode) {
        switch (visualizationMode) {
            case DECAY:
                return Math.max(lastRows - currentRows, 0);
            case INCREASE:
                return Math.max(nextRows - currentRows, 0);
            default:
                return 0;
        }
    }

}
