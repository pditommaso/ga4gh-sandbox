package ga4gh

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.model.Ga4ghWesServiceInfo
import io.swagger.model.Ga4ghWesState
import io.swagger.model.Ga4ghWesWorkflowDesc
import io.swagger.model.Ga4ghWesWorkflowListResponse
import io.swagger.model.Ga4ghWesWorkflowLog
import io.swagger.model.Ga4ghWesWorkflowRequest
import io.swagger.model.Ga4ghWesWorkflowRunId
import io.swagger.model.Ga4ghWesWorkflowStatus
/**
 * Implements WES API service
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
@Controller('/ga4gh/wes/v1')
class WesController {

    @Get("/service-info")
    HttpResponse<Ga4ghWesServiceInfo> getServiceInfo() {
        def resp = new Ga4ghWesServiceInfo()
        resp.setEngineVersions( nextflow: '1.0' )
        resp.setSupportedFilesystemProtocols(['file'])
        HttpResponse.created(resp)
    }

    /**
     * Retrieve a workflow instance status
     *
     * @param workflow_id
     * @return
     */
    @Get('/workflows/{workflow_id}/status')
    HttpResponse<Ga4ghWesWorkflowStatus> getWorkflowStatus(String workflow_id) {
        def resp = new Ga4ghWesWorkflowStatus()
        resp.setWorkflowId(workflow_id)
        resp.setState( Ga4ghWesState.COMPLETE )
        HttpResponse.created(resp)
    }

    /**
     * List all workflow instances
     *
     * @param page_size
     * @param page_token
     * @param key_value_search
     * @return
     */
    @Get('/workflows')
    HttpResponse<Ga4ghWesWorkflowListResponse> listWorkflows(Long page_size, String page_token, String key_value_search) {

        println "List page=$page_size; token=$page_token; key=$key_value_search"

        def resp = new Ga4ghWesWorkflowListResponse()

        def w1 = new Ga4ghWesWorkflowDesc()
        w1.setWorkflowId('3829')
        w1.setState(Ga4ghWesState.COMPLETE )

        def w2 = new Ga4ghWesWorkflowDesc()
        w2.setWorkflowId('8932')
        w2.setState(Ga4ghWesState.RUNNING )

        resp.setWorkflows( [w1, w2] )
        HttpResponse.created(resp)
    }

    /**
     * Retrieve a workflow log
     *
     * @param workflow_id
     * @return
     */
    @Get("/workflows/{workflow_id}")
    HttpResponse<Ga4ghWesWorkflowLog> getWorkflowLog(String workflow_id) {
        def resp = new Ga4ghWesWorkflowLog()
        resp.setWorkflowId(workflow_id)
        resp.setState( Ga4ghWesState.RUNNING )
        HttpResponse.created(resp)
    }

    /**
     * Run a workflow, this endpoint will allow you to create a new workflow request and retrieve its tracking ID to monitor its progress.  An important assumption in this endpoint is that the workflow_params JSON will include parameterizations along with input and output files.  The latter two may be on S3, Google object storage, local filesystems, etc.  This specification makes no distinction.  However, it is assumed that the submitter is using URLs that this system both understands and can access. For Amazon S3, this could be accomplished by given the credentials associated with a WES service access to a particular bucket.  The details are important for a production system and user on-boarding but outside the scope of this spec.
     *
     */
    @Post(uri="/workflows")
    HttpResponse<Ga4ghWesWorkflowRunId> runWorkflow(Ga4ghWesWorkflowRequest body) {
        println "execute this guy: $body"
        def resp = new Ga4ghWesWorkflowRunId()
        resp.setWorkflowId('7584')
        HttpResponse.created(resp)
    }

    /**
     * Delete a workflow instance
     *
     * @param workflow_id
     * @return
     */
    @Delete("/workflows/{workflow_id}")
    HttpResponse<Ga4ghWesWorkflowRunId> cancelJob(String workflow_id) {
        def resp = new Ga4ghWesWorkflowRunId()
        resp.setWorkflowId(workflow_id)
        HttpResponse.created(resp)
    }
}
