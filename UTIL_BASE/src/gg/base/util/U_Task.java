package gg.base.util;

import java.io.*;
import java.util.*;

import gg.base.text.*;

import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.vivado.util.L_Vivado.*;
import static gg.base.util.U_Files.*;

public interface U_Task {

    public static int runDirective(String directive, Text response, SMap environment, File directory, String... arguments) {	
    	SList command = new SList("cmd.exe", "/c", directive);
    	command.addAll(arguments);
       	return run(response, environment, directory, command);
    }

    public static int runApplication(File application, Text response, SMap environment, File directory, String... arguments) {	
    	SList command = new SList(getFilePath(application));
    	command.addAll(arguments);
       	return run(response, environment, directory, command);
	}
 
    public static int run(Text response, SMap environment, File directory, String... command) {	
    	return run(response, environment, directory, Arrays.asList(command));
    }
    
    public static int run(Text response, SMap environment, File directory, List<String> command) {	
    	int exitCode = -10000;
        try {
prn_("U_Task.run: command execute in \n\t" + getFilePath(directory), command);
       	
            ProcessBuilder pb = new ProcessBuilder();
            Map<String, String> env = pb.environment();
            env.putAll(environment);
            if (directory != null) pb.directory(directory);
            pb.command(command);
            pb.redirectErrorStream(true);
            
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) response.add(line);  // capture command output
 
            exitCode = process.waitFor();
prn_("U_Task.run: response", response);
        } catch (Exception ex) { assertion(ex, "Unexpected"); }
        
prn_("U_Task.run: exit code: " + exitCode);
        return exitCode;    	
    }

}
