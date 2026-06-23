package gg.base.util;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Files.checkSrcDirectory;
import static gg.base.util.U_Print.prn;
import static gg.base.util.U_Print.prn_;
import static gg.base.util.U_Streams.closeReader;
import static gg.base.util.U_Streams.getReader;
import static gg.base.util.U_Text.*;
import static gg.base.util.U_Print.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
//import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.io.FileUtils;

import gg.base.text.SList;
import gg.base.text.SSet;
import gg.base.text.Text;

public interface U_Files {

	static String getFileName(File f) {
		String[] ss = splitFileNameAndExtension(f);
		return ss[0];
	}
	
	static String getFileExtension(File f) {
		String[] ss = splitFileNameAndExtension(f);
		return ss[1];
	}
	
	static String[] splitFileNameAndExtension(File f) {
		String s = f.getName();
		int p = s.lastIndexOf(".");
		return new String[] {s.substring(0, p), s.substring(0, p)};		
	}

	static void replaceFileExtension(File dir, String oldExt, String newExt) {
		for (File f : dir.listFiles()) {
			if (f.isDirectory())
				replaceFileExtension(f, oldExt, newExt);
			else {
				String oldName = f.getName();
				int p = oldName.lastIndexOf(oldExt);
				if (p < 0)
					continue;
				String newName = oldName.substring(0, p);
				File newFile = new File(dir, newName + newExt);
				f.renameTo(newFile);
			}
		}
	}

	static ArrayList<File> getSubDirectories(File dir) {
		ArrayList<File> subDirs = new ArrayList<>();
		addSubDirectories(dir, subDirs);
		return subDirs;
	}

	static void addSubDirectories(File dir, ArrayList<File> subDirs) {
		prn("SubDir[" + subDirs.size() + "]: " + dir.getAbsolutePath());
		for (File sub : dir.listFiles()) {
			if (sub.isDirectory()) {
				subDirs.add(sub);
				addSubDirectories(sub, subDirs);
			}
		}
	}

	static ArrayList<File> getDirectories(Collection<File> files) {
		ArrayList<File> dirs = new ArrayList<>();
		addDirectories(files, dirs);
		return dirs;
	}

	static void addDirectories(Collection<File> files, ArrayList<File> dirs) {
		for (File f : files)
			dirs.add(f.getParentFile());
	}

	static SSet getDirectoryPaths(Collection<File> files) {
		SSet dirPaths = new SSet();
		addDirectoryPaths(files, dirPaths);
		return dirPaths;
	}

	static void addDirectoryPaths(Collection<File> files, SSet dirPaths) {
		for (File f : files)
			dirPaths.add(f.getParent());
	}

	static SSet getParentPaths(Collection<String> filePaths) {
		SSet dirPaths = new SSet();
		addParentPaths(filePaths, dirPaths);
		return dirPaths;
	}

	static void addParentPaths(Collection<String> filePaths, SSet dirPaths) {
		for (String filePath : filePaths)
			dirPaths.add(new File(filePath).getParent());
	}

	static void selectPathsByName(Collection<String> filePaths, SSet set, String... patterns) {
		for (String filePath : filePaths) {
			File f = new File(filePath);
			String fileName = f.getName();
			for (String pattern : patterns) {
				boolean inverse = pattern.startsWith("!");
				if (inverse)
					pattern = pattern.substring(1);
				boolean match = (fileName.indexOf(pattern) >= 0);
				if (match ^ inverse) {
//prn(f);
					set.add(f.getAbsolutePath());
				}
			}

		}
	}

//	static ArrayList<File> getDirectories(File dir, String... ss) {
//		ArrayList<File> dirs = new ArrayList<>();
//		getDirectories(dir, dirs, Arrays.asList(ss));
//		return dirs;
//	}
//
//	static ArrayList<File> getDirectories(File dir, Collection<String> cc) {
//		ArrayList<File> dirs = new ArrayList<>();
//		getDirectories(dir, dirs, cc);
//		return dirs;
//	}
//
//	static void getDirectories(File dir, ArrayList<File> dirs, Collection<String> cc) {
//		prn_("Dir[" + dirs.size() + "]: " + dir.getAbsolutePath());
//		for (File sub : dir.listFiles()) {
//			if (sub.isDirectory()) {
//				String s = sub.getAbsolutePath();
//				boolean flag = true;
//				for (String c : cc) {
//					if (c.startsWith("!")) {
//						if (s.indexOf(c.substring(1)) > 0) { flag = false; break; }
//					} else {
//						if (s.indexOf(c) > 0) { break; }
//					}
//				}
//				if (flag) {
//					dirs.add(sub);
//					getDirectories(sub, dirs, cc);
//				}
//			}
//		}
//	}

	static String getRelativeFilePath(File dir, File f) {
		String path = f.getPath();
		String dirPath = dir.getPath();
		int L = dirPath.length();
		if (path.startsWith(dirPath))
			return path.substring(L);
		return path;
	}

	static String getFilePath(File f) {
		String s = null;
//		try {
//prn(false, "file " + f);
		s = f.getAbsolutePath();
//prn(false, "path " + f);
//		} catch (Exception ex) {
//			String msg = "Can not get absolute path\n\tfrom: " + f;
//			assertion(ex, msg);
//		}
		String[] ss = s.split("\\\\");
//prn(false, "ss " + ss.length);
//for (String t : ss) prn(false, t);
		List<String> L = new ArrayList<>(Arrays.asList(ss));
//prn(false, "L " + L.size(), L);
		s = L.remove(0);
		for (String t : L)
			s += "/" + t;
		return s;

	}

	static File backup(File dstSpace, File srcDir) {
		String s = sortableDate("!", "_", "");
		File folder = new File(dstSpace, s);
		File dstDir = copyDirectory(folder, srcDir);
		deleteDirectory(srcDir);
		return dstDir;
	}

	static File copyDirectory(File dstSpace, File srcDir) {
		// pause("Copy directory\n\tspace: " + dstSpace + "\n\t src: " + srcDir);
		String srcName = srcDir.getName();
		File dstDir = new File(dstSpace, srcName);
		try {
			FileUtils.copyDirectory(srcDir, dstDir);
		} catch (Exception ex) {
			String msg = "Can not copy directory\n\tfrom: " + srcDir + "\n\t  to: " + dstDir;
			assertion(ex, msg);
		}
		return dstDir;
	}

	static File moveDirectoryToSpace(File srcDir, File dstSpace) {
//brk("Copy directory\n\tspace: " + dstSpace + "\n\t src: " + srcDir);
		String srcName = srcDir.getName();
		File dstDir = new File(dstSpace, srcName);
		renameDirectory(srcDir, dstDir);
		return dstDir;
	}

	static void renameDirectory(File srcDir, File dstDir) {
		try {
			FileUtils.moveDirectory(srcDir, dstDir);
		} catch (Exception ex) {
			String msg = "Can not move directory\n\tfrom: " + srcDir + "\n\t  to: " + dstDir;
			assertion(ex, msg);
		}
	}

	static int renameFileExtension(File dir, String ext, String newExt) {
		int n = 0;
		for (File file : dir.listFiles())
			if (file.isFile()) {
				String filePathName = file.getPath();
				if (filePathName.endsWith(ext)) {
					int p = filePathName.lastIndexOf(ext);
					String newFilePathName = filePathName.substring(0, p) + newExt;
					File newFile = new File(newFilePathName);
					try {
						FileUtils.moveFile(file, newFile);
					} catch (Exception ex) {
						String msg = "Can not move file\n\tfrom: " + file + "\n\t  to: " + newFile;
						assertion(ex, msg);
					}
					n++;
//prn(false, "renameFileExtension new file " + n + " " + file.renameTo(newFile));
				}
			} else if (file.isDirectory())
				n += renameFileExtension(file, ext, newExt); // subdir
		return n;
	}

	static void deleteDirectory(File dir) {
		try {
			FileUtils.deleteDirectory(dir);
		} catch (Exception ex) {
			assertion(ex, "Can not delete directory " + dir);
		}
		prn(false, "Directory " + dir + " is deleted");
	}

	static File checkFilePath(String filePath) {
		assertion(filePath != null, "Invalid file path NULL");
		return checkFile(new File(filePath));
	}

	static ArrayList<File> checkFilePaths(Collection<String> filePaths) {
		assertion(filePaths != null, "Invalid file path collection NULL");
		ArrayList<File> files = new ArrayList<>();
		for (String filePath : filePaths)
			files.add(checkFile(new File(filePath)));
		return files;
	}

	static File checkDirctoryPath(String dirPath) {
		assertion(dirPath != null, "Invalid dirctory path NULL");
		return checkSrcDirectory(new File(dirPath));
	}

	static ArrayList<File> checkDirctoryPaths(Collection<String> dirPaths) {
		assertion(dirPaths != null, "Invalid file dirctory collection NULL");
		ArrayList<File> files = new ArrayList<>();
		for (String dirPath : dirPaths)
			files.add(checkDirctoryPath(dirPath));
		return files;
	}

	static File checkFile(File f) {
		assertion(f != null, "Invalid file NULL");
		assertion(f.isFile(), "Cannot find file " + f.getAbsolutePath());
		return (f);
	}

	static File checkSrcDirectory(String dirPath) {
		assertion(dirPath != null, "Invalid directory path NULL");
		return checkDirectory(new File(dirPath), false);
	}

	static File checkSrcDirectory(File dir) {
		return checkDirectory(dir, false);
	}

	static File checkDstDirectory(String dirPath) {
		assertion(dirPath != null, "Invalid directory path NULL");
		return checkDirectory(new File(dirPath), true);
	}

	static File checkDstDirectory(File dir) {
		return checkDirectory(dir, true);
	}

	static File checkDirectory(File dir, boolean forDst) {
		assertion(dir != null, "Invalid directory NULL");
		if (forDst && !dir.isDirectory()) {
			boolean r = dir.mkdirs();
			prn("INFO: Create new directory " + dir.getAbsolutePath());
		}
		assertion(dir.isDirectory(), "Cannot find directory " + dir.getAbsolutePath());
		return dir;
	}

	static File copyFileToDirectory(File srcFile, File dstDir) {
		File dstFile = new File(dstDir, srcFile.getName());
		try {
			FileUtils.copyFileToDirectory(srcFile, dstDir);
		} catch (Exception ex) {
			assertion(ex, "Can not copy file " + srcFile + " to directory " + dstDir);
		}
		return dstFile;
	}

	static int copyFilesToDirectory(File srcDir, File dstDir) {
		int n = 0;
		for (File file : srcDir.listFiles())
			if (file.isFile()) {
				copyFileToDirectory(file, dstDir);
				n++;
			} else if (file.isDirectory())
				n += copyFilesToDirectory(file, dstDir); // subdir
		return n;
	}

	static void addSrcDirs(File rootDir, Collection<File> dirs, String... dirPaths) {
		addSrcDirs(rootDir, dirs, Arrays.asList(dirPaths));
	}

	static void addSrcDirs(File rootDir, Collection<File> dirs, Collection<String> dirPaths) {
		for (String dirPath : dirPaths) {
			File dir = new File(dirPath);
			if (!dir.isDirectory())
				if (rootDir != null)
					dir = checkDirectory(new File(rootDir, dirPath), false);
			if (dir.isDirectory())
				dirs.add(dir);
		}
	}

	static void addDstDirs(Collection<File> dirs, String... dirPaths) {
		addDstDirs(dirs, Arrays.asList(dirPaths));
	}

	static void addDstDirs(Collection<File> dirs, Collection<String> dirPaths) {
		for (String dirPath : dirPaths) {
			File dir = checkDirectory(new File(dirPath), true);
			if (dir != null)
				dirs.add(dir);
		}
	}

	static File findFile(String fileName, File rootDir, String... searchDirPaths) {
		return findFile(rootDir, Arrays.asList(searchDirPaths), fileName);
	}

	static File findFile(File rootDir, Collection<String> searchDirPaths, String fileName) {
		LinkedHashSet<File> searchDirs = new LinkedHashSet<>();
		addSrcDirs(rootDir, searchDirs, searchDirPaths);
//		prn("searchDirPaths", searchDirPaths);
//		prn("searchDirs", searchDirs); brk();
		return findFile(fileName, searchDirs);
	}

	static File findFile(String fileName, Collection<File> searchDirs) {
		return findFile(fileName, searchDirs, -1);
	}

	static File findFile(String fileName, Collection<File> searchDirs, int level, String... dirNames) {
		File f = new File(fileName);
		if (f.isFile())
			return f;
		for (File dir : searchDirs) {
//prn(fileName + " in " + dir);
			f = findFile(dir, fileName, level);
			if (f != null)
				return f;
		}
		return null;
	};

	static void addFilesDeep(File dir, Collection<File> list) { // brk(fileName + " in " + dir);
		for (File f : dir.listFiles())
			if (f.isDirectory())
				addFilesDeep(f, list);
			else
				list.add(f);
	}

	static ArrayList<File> listFilesDeep(File dir) { // brk(fileName + " in " + dir);
		ArrayList<File> list = new ArrayList<>();
		addFilesDeep(dir, list);
		return list;
	}

	static File findFileDeep(File dir, String fileName) { // brk(fileName + " in " + dir);
		return findFile(dir, fileName, -1);
	}

	boolean DEBUG_FIND_FILE_DEEP = false;// true;//

	static File findFile(File dir, String fileName, int level) { // prn(fileName + " in " + dir); //brk(fileName + " in
																	// " + dir);
		String tap = getTap(level);
		for (int i = 0; i < level; i++)
			tap += TAP;
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				f = findFile(f, fileName, (level < 0) ? level : level++);
				if (f != null)
					return f;
			} else {
				String s = f.getName();
				if (DEBUG_FIND_FILE_DEEP && (tap != null))
					prn(TAP + tap + s);
				if (s.equals(fileName))
					return f;
			}
		}
		return null;
	}

	public static File findDirectory(String dirName, Collection<File> searchDirs) {
//prn_("INFO: dirName " + dirName);
		File d = new File(dirName);
//prn_("INFO: dir " + getFilePath(d) + " = " + d.isDirectory());
		if (d.isDirectory())
			return d;
		for (File dir : searchDirs) {
			d = new File(dir, dirName); // deep down hierarchy
//prn_("INFO: sub dir " + getFilePath(d) + " = " + d.isDirectory());
			if (d.isDirectory())
				return d;
		}
		return null;
	};

	static LinkedHashSet<File> getDirectories(List<String> paths) { // update paths with unique absolute paths of
																	// existing directories
		LinkedHashSet<File> dirs = new LinkedHashSet<>();
		int n = paths.size();
		for (int i = 0; i < n; i++) {
			String incdir = paths.remove(0);
			File dir = findDirectory(incdir, dirs);
			if (dir == null) {
				prn("WARNING: Directory not found:\n\t" + incdir);
				continue;
			}
			if (dirs.add(dir)) {
				incdir = getFilePath(dir.getAbsoluteFile());
				paths.add(incdir);
			}
		}
		return dirs;
	};

	static Text readFileIncluding(String fileName, Collection<File> dirs, String directiveInclude) {
		ArrayList<File> files = new ArrayList<>();
		Stack<BufferedReader> readers = new Stack<>();
		Text text = new Text();
		readFileIncluding(text, fileName, dirs, files, readers, directiveInclude);
		return text;
	}

	static void readFileIncluding(Text text, String fileName, Collection<File> dirs, Collection<File> files,
			Stack<BufferedReader> readers, String directiveInclude) {
		File file = findFile(fileName, dirs);
		if (file == null) {
			prn(true, "WARNING: cannot find file '" + fileName + "'");
			return;
		}
		files.add(file);
		BufferedReader src = getReader(file);
		while (true) {
			String s = null;
			try {
				s = src.readLine();
			} catch (IOException ex) {
				assertion(ex, "Cannot read file\n\t" + file);
			}
			if (s == null)
				break;
			if (s.trim().startsWith(directiveInclude)) {
				String incFileName = s.substring(s.indexOf("\"") + 1, s.length() - 1);
				readers.push(src);
				readFileIncluding(text, incFileName, dirs, files, readers, directiveInclude);
				src = readers.pop();
				continue;
			}
			text.add(s);
		}
		closeReader(src);
	}

}
