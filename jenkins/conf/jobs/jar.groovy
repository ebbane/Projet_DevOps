#!groovy
println('------------------------------------------------------------------Import Job CI/Jar')
def pipelineScript = new File('/var/jenkins_config/jobs/jar-pipeline.groovy').getText("UTF-8")

pipelineJob('CI/Jar') {
    description("Job jar pipeline")
    parameters {
        stringParam {
            name('BRANCH')
            defaultValue('master')
            description("Select branch fron github repo")
            trim(false)
        }
        booleanParam{
            name('SKIP_TESTS')
            defaultValue(false)
            description("Skip unit and integration tests")
        }
        choiceParam('VERSION_TYPE', ['SNAPSHOT', 'RELEASE'], "SNAPSHOT ou RELEASE")
        stringParam {
            name('VERSION')
            defaultValue("SNAPSHOT")
            description("Jar type")
            trim(false)
        }
        stringParam {
            name('VERSION')
            defaultValue('1.0.0')
            description("Jar version")
            trim(false)
        }
    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}