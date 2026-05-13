tasks.register("testAllModules") {
    dependsOn(subprojects.map { it.tasks.named("test") })
}