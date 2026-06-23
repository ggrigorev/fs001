package gg.base.util;

import java.io.*;

import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;

import gg.base.text.Text;

public interface U_System {

	static int RunBashScript_RT(String execPath, Text execOut) {// "/path/to/your/script.sh"
		int exitCode = -10;
		try {
			Process process = Runtime.getRuntime().exec(new String[] { "/bin/bash", execPath });
			// Reading the output
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
//prn_(line);
					execOut.add(line);
				}
			}

			exitCode = process.waitFor();
//prn("Exit Code: " + exitCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exitCode;
	}

	static int RunBashScript(String scriptPath, String execPath, Text execOut) {// "/path/to/your/script.sh"
		int exitCode = -10;
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", scriptPath);
            processBuilder.directory(new File(execPath)); // Set the working directory
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			// Reading the output
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					prn_(line);
					execOut.add(line);
				}
			}

			exitCode = process.waitFor();
			prn("Exit Code: " + exitCode);
		} catch (Exception ex) {
			assertion(ex);
		}
		return exitCode;
	}

}
