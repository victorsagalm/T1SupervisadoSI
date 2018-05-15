package Ejemplo;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.lazy.IBk;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class Main {

	public static void main(String args[]) {
		try {
			/*Lectura*/
			BufferedReader readerTrain = new BufferedReader(
					new FileReader(args[0]));
			BufferedReader readerTest = new BufferedReader(
					new FileReader(args[1]));
			/*Declara Train y Test*/
			Instances Train = new Instances(readerTrain);
			Train.setClassIndex(Train.numAttributes() - 1);
			Instances Test = new Instances(readerTest);
			Test.setClassIndex(Test.numAttributes()-1);
			readerTrain.close();
			readerTest.close();
			/*Clasificador*/
			IBk knn = new IBk();
			/*Opciones, se obtienen de la interfaz grafica*/
			String [] options = {"-K", "1", "-W", "0", "-A","weka.core.neighboursearch.LinearNNSearch"};
			knn.setOptions(options);
			knn.buildClassifier(Train);
			/*Arbol*/
			System.out.println(knn.toString());
			/*Evaluacion*/
			Evaluation eval = new Evaluation(Train);

			/*Predicciones*/
			
			for (int i = 0; i < Test.numInstances(); i++) {
				eval.evaluateModelOnce(knn,Test.instance(i));
				
				
				double pred = knn.classifyInstance(Test.instance(i));

				System.out.print("ID: " + Test.instance(i).value(0));
				System.out.print(", actual: " + Test.classAttribute().value((int) Test.instance(i).classValue()));
				System.out.println(", predicted: " + Test.classAttribute().value((int) pred));
				
			}
			
			/*Matriz de Confusion*/
			/*Imprime toda*/
			System.out.println(eval.toMatrixString());
			/*Imprime por valor*/
			double valoresMatriz[][]=eval.confusionMatrix();
			int filas=valoresMatriz.length;
			for(int i=0;i<filas;i++) {
				for(int j=0;j<valoresMatriz[i].length;j++) {
					System.out.println(valoresMatriz[i][j]);
				}
			}
			
			/*Obtener clases*/
			double a[]=eval.getClassPriors();
			/*FALTA OBTENER VALOR POR CLASES*/
		
			
			/*Valores Totales*/
			System.out.println("TPR: "+eval.weightedTruePositiveRate());
			System.out.println("TNR: "+eval.weightedTrueNegativeRate());
			System.out.println("FPR: "+eval.weightedFalsePositiveRate());
			System.out.println("FNR: "+eval.weightedFalseNegativeRate());
			System.out.println("Precision: "+eval.weightedPrecision());
			System.out.println("Recall: "+eval.weightedRecall());
			System.out.println("F Score: "+eval.weightedFMeasure());

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
