package gg.vivado.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import gg.base.text.*;
//import gg.base.util.ExternalTask;

import static gg.proj.util.L_Project.*;
import static gg.vivado.util.L_Vivado.*;

public class VivadoTask {//extends ExternalTask {
//
//    public static int runVivadoTcl(Text answ, SMap env, String toolPath, String workPath, String scriptPath, String... args) {	
//    	int exitCode = -10000;
//        try {
//        	SList cmd = new SList(
//        			toolPath, // VIVADO_TOOL_EXE(), 
//        			"-mode", "batch",
//                    "-source", scriptPath
//        			);
//        	if (args.length > 0) {
//        		cmd.add("-tclargs");
//        		for (String arg : args) cmd.add(arg);
//        	}
//        	
//            ProcessBuilder pb = new ProcessBuilder();
//            pb.environment(env);
//            pb.directory(new File(workPath));
//            pb.command(cmd);
//            pb.redirectErrorStream(true);
//            
//            Process process = pb.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) answ.add(line);  // capture Vivado output
// 
//            exitCode = process.waitFor();
//            System.out.println("Vivado exited with code: " + exitCode);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return exitCode;
//    }

}
