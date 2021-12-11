#!groovy
println('------------------------------------------------------------------Import Job CI/terraform-destroy')
def pipelineScript = new File('/var/jenkins_config/jobs/terraform-destroy-pipeline.groovy').getText("UTF-8")

pipelineJob('CI/terraform-destroy') {
    description("Job to destroy AWS instance")

    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}