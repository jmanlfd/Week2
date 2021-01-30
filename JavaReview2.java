package review2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JavaReview2 {

	public static void main(String[] args) {
		int[][] temperatures = new int[31][4];
		File csv = new File("SLCDecember2020Temperatures.csv");
		double averageHi = 0, averageLo = 0;
		int[] highest = { 0, 0 };
		int[] lowest = { 0, Integer.MAX_VALUE };

		try {
			BufferedReader in = new BufferedReader(new FileReader(csv));

			for (int i = 0; i < 31; ++i) {
				String[] numbers = in.readLine().split(",");
				
				for (int j = 0; j < 3; ++j) {
					temperatures[i][j] = Integer.parseInt(numbers[j]);
					
					if (j == 1) {
						averageHi += temperatures[i][1];
						if (temperatures[i][1] > highest[1]) {
							highest[0] = temperatures[i][0];
							highest[1] = temperatures[i][1];
						}
					} else if (j == 2) {
						averageLo += temperatures[i][j];
						if (temperatures[i][2] < lowest[1]) {
							lowest[0] = temperatures[i][0];
							lowest[1] = temperatures[i][2];
						}
					}
				}
				temperatures[i][3] = temperatures[i][1] - temperatures[i][2];

			}
			in.close();
			
			averageHi = Math.round((averageHi / 31) * 10) / 10d;
			averageLo = Math.round((averageLo / 31) * 10) / 10d;

			StringBuilder sb = new StringBuilder(1024);
			sb.append("--------------------------------------------------------------\n");
			sb.append("December 2020: Temperatures in Utah\n");
			sb.append("--------------------------------------------------------------\n");
			sb.append("Day  High Low  Variance\n");
			sb.append("--------------------------------------------------------------\n");

			for (int i = 0; i < 31; ++i) {
				sb.append(temperatures[i][0]);

				if (temperatures[i][0] < 10) {
					sb.append("    ");
				} else {
					sb.append("   ");
				}

				for (int j = 1; j < 4; ++j) {
					if (j < 3) {
						if (temperatures[i][j] < 10) {
							sb.append(temperatures[i][j] + "    ");
						} else {
							sb.append(temperatures[i][j] + "   ");
						}
					} else {
						sb.append(temperatures[i][j] + "\n");
					}

				}

			}

			sb.append("--------------------------------------------------------------\n");
			sb.append("December Highest Temperature: 12/" + highest[0] + ": " + highest[1] + " Average Hi: " + averageHi + "\n");
			sb.append("December Lowest Temperature: 12/" + lowest[0] + ": " + lowest[1] + " Average Lo: " + averageLo + "\n");
			sb.append("--------------------------------------------------------------\n");
			sb.append("Graph\n");
			sb.append("--------------------------------------------------------------\n");
			sb.append("      1   5    10   15   20   25   30   35   40   45   50\n");
			sb.append("      |   |    |    |    |    |    |    |    |    |    |\n");
			sb.append("--------------------------------------------------------------\n");
			for (int i = 0; i < 31; ++i) {
				sb.append(temperatures[i][0] + " ");
				if (temperatures[i][0] < 10) {
					sb.append("  ");
				} else {
					sb.append(" ");
				}
				sb.append("Hi " + "+".repeat(temperatures[i][1]) + "\n");
				sb.append("    Lo " + "-".repeat(temperatures[i][2]) + "\n");
			}
			sb.append("--------------------------------------------------------------\n");
			sb.append("     |   |    |    |    |    |    |    |    |    |    |\n");
			sb.append("     1   5    10   15   20   25   30   35   40   45   50\n");
			sb.append("--------------------------------------------------------------");

			System.out.println(sb.toString());
			
			File newFile = new File("TemperaturesReport.txt");
			if (!newFile.exists()) {
				newFile.createNewFile();
				FileWriter fw = new FileWriter("TemperaturesReport.txt");
				fw.write(sb.toString().replace("\n", "\r\n"));
				fw.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the CSV file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
