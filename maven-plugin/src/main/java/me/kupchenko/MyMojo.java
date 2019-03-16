package me.kupchenko;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

@Mojo(name = "check",
        executionStrategy = "always",
        threadSafe = true,
        defaultPhase = LifecyclePhase.VALIDATE)
public class MyMojo extends AbstractMojo {

    @Parameter(property = "targetFolder", defaultValue = "${project.basedir}")
    private File directory;
    @Parameter(property = "words", required = true)
    private String[] words;
    private Charset charset = Charset.defaultCharset();
    @Parameter(property = "include", defaultValue = "*.properties")
    private String include;

    public void execute() throws MojoExecutionException {
        // so long as this is empty, the build will succeed final
        final StringBuilder reports = new StringBuilder();
        try {
            final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/" + include);
            Files.walkFileTree(Paths.get(directory.toURI()), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    FileVisitResult result = super.visitFile(file, attrs);
                    if (matcher.matches(file)) {
                        List<String> lines = Files.readAllLines(file, charset);
                        for (String line : lines) {
                            for (String word : words) {
                                if (line.contains(word)) {
                                    reports.append(report(file, lines.indexOf(line), word));
                                }
                            }
                        }
                    }
                    return result;
                }
            });
        } catch (IOException e) {
            getLog().error(e);
        }
        if (reports.length() > 0) {
            getLog().error(reports);
            throw new MojoExecutionException("Prohibited word found");
        }
    }

    private String report(Path file, int i, String word) {
        return word;
    }
}
