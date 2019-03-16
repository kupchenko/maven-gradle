package me.kupchenko

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

class TestPluginTask extends DefaultTask {

    @InputFiles
    FileCollection filesToSearch;
    @Input
    List<String> words;
    @Input
    boolean fail;

    @TaskAction
    void doSomething() {
        def found = filesToSearch.any { File file ->
            def result = words.findAll { file.text.contains(it) }
            if (result) {
                logger.warn("WARNING! $result found in $file!")
            }
            result
        }
        if (fail && found) {
            throw new GradleException("Found prohibited words. Please check console!")
        }
    }
}
