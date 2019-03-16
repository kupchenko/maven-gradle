package me.kupchenko

import org.gradle.api.Plugin
import org.gradle.api.Project

class TestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        TestPluginExtension extension = project.extensions.create("pluginExtension", TestPluginExtension);
        project.tasks.create("pluginTask", TestPluginTask.class) {
            project.afterEvaluate {
                println project.sourceSets.main.groovy.files
                filesToSearch = project.files(project.sourceSets.main.java.files)
                fail = project.pluginExtension.fail
                words = project.pluginExtension.words
            }
            it.outputs.upToDateWhen {true}
        };
    }
}