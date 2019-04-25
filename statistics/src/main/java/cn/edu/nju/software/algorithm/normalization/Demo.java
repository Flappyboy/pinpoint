package cn.edu.nju.software.algorithm.normalization;

public class Demo {
    /**
     * 线性归一化 公式：X(norm) = (X - min) / (max - min)
     *
     * @param points 原始数据
     * @return 归一化后的数据
     */
    public static double[][] normalize4Scale(double[][] points) {
        if (points == null || points.length < 1) {
            return points;
        }
        double[][] p = new double[points.length][points[0].length];
        double[] matrixJ;
        double maxV;
        double minV;
        for (int j = 0; j < points[0].length; j++) {
            matrixJ = getMatrixCol(points, j);
            maxV = maxV(matrixJ);
            minV = minV(matrixJ);
            for (int i = 0; i < points.length; i++) {
                p[i][j] = maxV == minV ? minV : (points[i][j] - minV) / (maxV - minV);
            }
        }
        return p;
    }

    /**
     * 获取矩阵的某一列
     *
     * @param points points
     * @param column column
     * @return double[]
     */
    public static double[] getMatrixCol(double[][] points, int column) {
        double[] matrixJ = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            matrixJ[i] = points[i][column];
        }
        return matrixJ;
    }

    /**
     * 获取数组中的最小值
     *
     * @param matrixJ matrixJ
     * @return v
     */
    public static double minV(double[] matrixJ) {
        double v = matrixJ[0];
        for (int i = 0; i < matrixJ.length; i++) {
            if (matrixJ[i] < v) {
                v = matrixJ[i];
            }
        }
        return v;
    }

    /**
     * 获取数组中的最大值
     *
     * @param matrixJ matrixJ
     * @return v
     */
    public static double maxV(double[] matrixJ) {
        double v = matrixJ[0];
        for (int i = 0; i < matrixJ.length; i++) {
            if (matrixJ[i] > v) {
                v = matrixJ[i];
            }
        }
        return v;
    }




    public static void main(String[] args) {
        double[][] points = {{2, 5, 7}, {3, 1, 5}, {0, 27, 11}, {109, 6, 1}};
        double[][] p1 = normalize4Scale(points);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println(points[i][j]+" -> "+p1[i][j]);
            }
        }


    }
}
