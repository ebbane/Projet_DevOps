#!groovy
println('------------------------------------------------------------------Import Job CI/terraform')
def pipelineScript = new File('/var/jenkins_config/jobs/terraform-pipeline.groovy').getText("UTF-8")

pipelineJob('CI/terraform') {
    description("Job terraform")
    parameters {
        booleanParam{
            name('SKIP_DESTROY')
            defaultValue(false)
            description("Destroy AWS instance")
        }

    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}