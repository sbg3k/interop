import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.values.ValueFactoryFactory;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import io.usethesource.vallang.IValue;
import io.usethesource.vallang.IValueFactory;
import io.usethesource.vallang.impl.persistent.ValueFactory;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.load.RascalSearchPath;
import org.rascalmpl.interpreter.load.StandardLibraryContributor;

public class RascalWrapper {
    private final Evaluator evaluator;
    private final NullRascalMonitor monitor;
    private final ByteArrayOutputStream stdout;
    private final ByteArrayOutputStream stderr;
    private final IValueFactory valueFactory;

    public RascalWrapper() {
        valueFactory = ValueFactory.getInstance();

        stdout = new ByteArrayOutputStream();
        stderr = new ByteArrayOutputStream();
        PrintStream psOut = new PrintStream(stdout);
        PrintStream psErr = new PrintStream(stderr);

        // Redirect System.out and System.err to capture output
        System.setOut(psOut);
        System.setErr(psErr);

        monitor = new NullRascalMonitor();
        GlobalEnvironment heap = new GlobalEnvironment();
        ModuleEnvironment root = new ModuleEnvironment("___", heap);

        // Initialize RascalSearchPath
        RascalSearchPath searchPath = new RascalSearchPath();
        searchPath.addPathContributor(StandardLibraryContributor.getInstance());

        // Create a list with the current class loader
        List<ClassLoader> classLoaderList = Collections.singletonList(getClass().getClassLoader());

        evaluator = new Evaluator(valueFactory, null, psOut, psErr, root, heap, classLoaderList, searchPath);

        // Load IO module
        evaluator.doImport(monitor, "IO");
    }

    public void executeRascalCode(String code) {
        evaluator.eval(monitor, code, URIUtil.rootLocation("cwd"));
        System.out.flush();  // Ensure the output is flushed
        System.err.flush();  // Ensure the error output is flushed
    }

    public void executeRascalFile(String filePath) {
        try {
            String code = new String(Files.readAllBytes(Paths.get(filePath)));
            executeRascalCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IValue callFunction(String moduleName, String functionName, IValue... args) {
        evaluator.doImport(monitor, moduleName);
        return evaluator.call(functionName, args);
    }

    public String getOutput() {
        System.out.flush();
        System.err.flush();
        return stdout.toString() + stderr.toString();
    }

    public static void main(String[] args) {
        RascalWrapper wrapper = new RascalWrapper();
        if (args.length > 0) {
            wrapper.executeRascalFile(args[0]);
        } else {
            wrapper.executeRascalCode("println(\"Hello, dynamic Rascal world!\");");
        }
        System.out.println("Captured Output:");
        System.out.println(wrapper.getOutput());
    }
}
