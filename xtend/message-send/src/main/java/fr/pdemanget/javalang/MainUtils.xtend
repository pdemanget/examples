package fr.pdemanget.javalang

import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileFilter
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.FilenameFilter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.io.StringWriter
import java.util.HashMap
import java.util.Map
import java.util.Properties
import java.util.regex.Matcher
import java.util.regex.Pattern

/** 
 * Some utilities for command-line application (CLI).
 * MainHelper loads commmand line arguments AND conf.properties in resources and current filepath.
 */
class MainUtils {
	protected static final String UTF_8 = "UTF-8"

	def static boolean isWindows() {
		return System::getProperty("os.name").toLowerCase().indexOf("win") >= 0
	}

	/* ======== FILEUTILS SECTION ==============*/
	static class WildCardFileFilter implements FilenameFilter, FileFilter {
		String _pattern

		new(String pattern) {
			_pattern = pattern.replace(".", "\\.")
			_pattern = pattern.replace("*", ".*").replace("?", ".")
		}

		override boolean accept(File file) {
			return Pattern::compile(_pattern).matcher(file.getName()).find()
		}

		override boolean accept(File pDir, String pName) {
			logError("filter ", pName)
			return Pattern::compile(_pattern, if(isWindows()) Pattern::CASE_INSENSITIVE else 0).matcher(pName).find()
		} 
   /*========================= END OF FILEUTILS SECTINO =======================*/
	}

	/** 
	 * Resolve path with a wildcard or a comma.
	 * example: file1,file2,file3 or xxx*.ext 
	 */
	def static String[] listFiles(String pWildcardPath) {
		if (pWildcardPath.contains(",")) {
			var String[] files = pWildcardPath.split(",")
			return files
		}
		if (pWildcardPath.contains("*")) {
			return dirList(pWildcardPath)
		}
		return (#[pWildcardPath] as String[])
	}

	def private static String[] dirList(String fname_finalParam_) {
		var fname = fname_finalParam_
		if (isWindows()) {
			fname = fname.replace(Character.valueOf('\\').charValue, Character.valueOf('/').charValue)
		}
		var String dirPath = ""
		if (fname.contains("/")) {
			var int lPosFilename = fname.lastIndexOf("/")
			dirPath = fname.substring(0, lPosFilename + 1)
			fname = fname.substring(lPosFilename + 1)
		}
		var File dir = new File(dirPath)
		return dir.list(new WildCardFileFilter(fname))
	}

	/** 
	 * Contraire du String_split.
	 */
	def static String merge(String[] src, String sep) {
		var StringBuffer result = new StringBuffer()
		var String lsep = ""
		for (String s : src) {
			result.append(lsep)
			result.append(s)
			lsep = sep
		}
		return result.toString()
	}

	/** 
	 * parse arguments nix-style.
	 */
	def static Map<String, String> parseArgs(String[] args) {
		var Map<String, String> param = new HashMap<String, String>()
		var String otherArgs = ""
		var String sep = ""
		for (var int i = 0; i < args.length - 1; i++) {
			if ({
				val _rdIndx_args = i
				args.get(_rdIndx_args)
			}.startsWith("--")) {
				param.put({
					val _rdIndx_args = i
					args.get(_rdIndx_args)
				}.substring(2), "")
			} else if ({
				val _rdIndx_args = i
				args.get(_rdIndx_args)
			}.startsWith("-")) {
				param.put({
					val _rdIndx_args = i
					args.get(_rdIndx_args)
				}.substring(1), {
					val _rdIndx_args = i + 1
					args.get(_rdIndx_args)
				})
				i++
			} else {
				otherArgs += sep + {
					val _rdIndx_args = i
					args.get(_rdIndx_args)
				}
				sep = ","
			}
		}
		if (args.length > 0) {
			if ({
				val _rdIndx_args = args.length - 1
				args.get(_rdIndx_args)
			}.startsWith("--")) {
				param.put({
					val _rdIndx_args = args.length - 1
					args.get(_rdIndx_args)
				}.substring(2), "")
			} else {
				otherArgs += sep + {
					val _rdIndx_args = args.length - 1
					args.get(_rdIndx_args)
				}
				sep = ","
			}
		}
		param.put("zargs", otherArgs)
		return param
	}

	def static Properties loadProperties(String pProp_finalParam_) {
		var pProp = pProp_finalParam_
		if (pProp === null) {
			pProp = "conf.properties"
		}
		var Properties lProp = new Properties()
		var ClassLoader lClassLoader = Thread::currentThread().getContextClassLoader()
		try {
			var InputStream lResourceAsStream = lClassLoader.getResourceAsStream("conf.properties")
			if (lResourceAsStream !== null) {
				lProp.load(lResourceAsStream)
			}
		} catch (FileNotFoundException e) {
			logError("Warning, expected properties file:", pProp)
		} catch (IOException e) {
			logError("IOException :", lProp, e)
		}

		return lProp
	}

	def static Properties loadPropertiesFile(String pProp_finalParam_) {
		var pProp = pProp_finalParam_
		if (pProp === null) {
			pProp = "conf.properties"
		}
		var Properties lProp = new Properties()
		try {
			lProp.load(new FileInputStream(pProp))
		} catch (FileNotFoundException e) {
			logError("Warning, seeking file:", pProp)
		} catch (IOException e) {
			logError("IOException :", lProp, e)
		}

		return lProp
	}

	def private static StringBuilder getMsg(Object... pObjects) {
		var StringBuilder lMsg = new StringBuilder()
		for (Object o : pObjects) {
			if (o !== null) {
				if (o instanceof Throwable) {
					var Throwable e = (o as Throwable)
					var StringWriter lStack = new StringWriter()
					e.printStackTrace(new PrintWriter(lStack))
					lMsg.append(e.getClass().getSimpleName())
					lMsg.append(":")
					lMsg.append(e.getMessage())
					lMsg.append(Character.valueOf('\n').charValue)
					lMsg.append(lStack)
				}
				lMsg.append(o)
				lMsg.append(Character.valueOf(' ').charValue)
			}
		}
		return lMsg
	}

	def static void logError(Object... pObjects) {
		System::err.println(getMsg(pObjects))
	}

	/** 
	 * Ecrit un fichier (initulis� cf. MessageFormat)
	 */
	def protected void write(String pOut_finalParam_, byte[] pData) {
		var pOut = pOut_finalParam_
		if (pOut === null) {
			pOut = "out.xml"
		}
		try {
			var FileOutputStream lOut
			lOut = new FileOutputStream(pOut)
			lOut.write(pData)
		} catch (FileNotFoundException e) {
			logError("File not found ", pOut, e)
			return;
		} catch (IOException e) {
			logError("File not writable ", pOut, e)
			return;
		}

		logError("File created:", pOut)
	}

	/** 
	 * inutilis� (copi� dans MessageFormat)
	 */
	def protected Map<String, String> parseVars(String pVar) {
		logError("var:", pVar)
		var String[] vars = pVar.split(",")
		var Map<String, String> result = new HashMap<String, String>()
		for (String ^var : vars) {
			var String[] vals = ^var.split("=")
			if (vals.length >= 2) {
				result.put(vals.get(0), vals.get(1))
			}
		}
		return result
	}

	/** 
	 * Lit le fichier � envoyer (jms ou MQ pour l'instant)
	 */
	def static String readFile(String pMsg) {
		var StringBuffer result = new StringBuffer()
		var BufferedReader lBis = null
		try {
			var String line
			lBis = new BufferedReader(new InputStreamReader(new FileInputStream(pMsg), UTF_8))
			while ((line = lBis.readLine()) !== null) {
				result.append(line)
				result.append("\n")
			}
		} catch (IOException e) {
			logError("Can't read file ", pMsg, e)
		} finally {
			try {
				if (lBis !== null) {
					lBis.close()
				}
			} catch (IOException e) {
				logError("Can't close file ", pMsg, e)
			}

		}
		return result.toString()
	}

	/** 
	 * inutilis� (copi� dans MessageFormat)
	 */
	def protected byte[] readReplace(String pMsg, Map<String, String> pVars) {
		var byte[] lData = null
		var ByteArrayOutputStream lBaos = new ByteArrayOutputStream(1024)
		var BufferedReader lBis = null
		try {
			val byte[] CRLF = "\n".getBytes(UTF_8)
			var String line
			lBis = new BufferedReader(new InputStreamReader(new FileInputStream(pMsg), UTF_8))
			while ((line = lBis.readLine()) !== null) {
				if (line.matches(".*\\$\\{[^\\}]+\\}.*")) {
					var String pattern = "\\$\\{([^\\}]+)\\}"
					var Pattern pat = Pattern::compile(pattern)
					var Matcher mat = pat.matcher(line)
					var int lPrevMatch = 0
					while (mat.find()) {
						var String lVar = mat.group(1)
						var String lReplace = pVars.get(lVar)
						if (lReplace === null) {
							logError("Undeclared variable:", lVar)
							lReplace = "UNDEFINED"
						}
						lBaos.write(line.substring(lPrevMatch, mat.start()).getBytes(UTF_8))
						lBaos.write(lReplace.getBytes(UTF_8))
						lPrevMatch = mat.end()
					}
					lBaos.write(line.substring(lPrevMatch).getBytes(UTF_8))
					lBaos.write(CRLF)
				} else {
					lBaos.write(line.getBytes(UTF_8))
					lBaos.write(CRLF)
				}
			}
			lData = lBaos.toByteArray()
		} catch (IOException e) {
			logError("Can't read file ", pMsg, e)
		} finally {
			try {
				if (lBis !== null) {
					lBis.close()
				}
				lBaos.close()
			} catch (IOException e) {
				logError("Can't close file ", pMsg, e)
			}

		}
		return lData
	}

	def static void writeFile(String pOut_finalParam_, String pData, boolean pAppend) {
		var pOut = pOut_finalParam_
		if (pOut === null) {
			pOut = "out.xml"
		}
		try {
			var FileWriter lOut
			lOut = new FileWriter(pOut, pAppend)
			lOut.write(pData)
			lOut.close()
		} catch (FileNotFoundException e) {
			MainUtils::logError("File not found ", pOut, e)
			return;
		} catch (IOException e) {
			MainUtils::logError("File not writable ", pOut, e)
			return;
		}

		MainUtils::logError("File created:", pOut)
	}

	def static void showUsage(String pAppName_finalParam_, String pUsage1_finalParam_, String pUsage2_finalParam_) {
		var pAppName = pAppName_finalParam_
		var pUsage1 = pUsage1_finalParam_
		var pUsage2 = pUsage2_finalParam_
		pAppName = ('''«pAppName»                     '''.toString).substring(0, 20)
		pUsage1 = ('''«pUsage1»                                                                                 '''.
			toString).substring(0, 75)
		pUsage2 = ('''«pUsage2»                                                                                 '''.
			toString).substring(0, 75)
		{
			logError('''/----------------------------------------------------------------------------\
|                  «pAppName»     |  MainUtils 2010                |
| Usage :                                   \--------------------------------|
| «pUsage1»|
| «pUsage2»|
\----------------------------------------------------------------------------/
'''.toString)
		}
	}

	def static void putAll(Map<String, String> pDest, Properties pProp) {
		for (Object lO : pProp.keySet()) {
			pDest.put((lO as String), (pProp.get(lO) as String))
		}
	}

	/** 
	 * Main delegation for parsing arguments and display usage.
	 * 2 default arguments:
	 * -conf file the loaded configuration file
	 * --help display usage
	 * zargs contains unnames arguments.
	 */
	def static Map<String, String> mainHelper(String[] args, String pAppName, String pUsage1, String pUsage2) {
		try {
			var Map<String, String> lParseArgs = MainUtils::parseArgs(args)
			var Map<String, String> lParam = new HashMap<String, String>()
			var Properties lLoadProperties = MainUtils::loadProperties(lParseArgs.get("conf"))
			var Properties lLoadProperties2 = MainUtils::loadPropertiesFile(lParseArgs.get("conf"))
			putAll(lParam, lLoadProperties)
			putAll(lParam, lLoadProperties2)
			lParam.putAll(lParseArgs)
			if (lParseArgs.get("help") !== null) {
				MainUtils::showUsage(pAppName, pUsage1, pUsage2)
				return null
			}
			return lParam
		} catch (Exception e) {
			MainUtils::showUsage(pAppName, pUsage1, pUsage2)
			MainUtils::logError(e)
			return null
		}

	}
}
