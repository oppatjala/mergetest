/*
 * Copyright (c) ITivityKids, Inc. All Rights Reserved.
 */
package com.itivitykids.reservation.test.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.itivitykids.reservation.test.solr.util.SolrHelper;
import org.junit.Assume;

/**
 * Test solr configuration.
 *
 * @author ovidio
 */
public class BasicHTTPSolrActivityCoreTest {

    public static SolrServer server;

    @BeforeClass
    public static void setup() {
        SolrDocumentList results = null;
        try {
            String url = "http://localhost:8993" + "/activities";
            HttpSolrServer httpserver = new HttpSolrServer(url);
            //httpserver.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
            //httpserver.setConnectionTimeout(5000); // 5 seconds to establish TCP
            // Setting the XML response parser is only required for cross
            // version compatibility and only when one side is 1.4.1 or
            // earlier and the other side is 3.1 or later.
            //httpserver.setParser(new XMLResponseParser()); // binary parser is used by default
            // The following settings are provided here for completeness.
            // They will not normally be required, and should only be used
            // after consulting javadocs to know whether they are truly required.
            //httpserver.setSoTimeout(1000);  // socket read timeout
            //httpserver.setDefaultMaxConnectionsPerHost(100);
            //httpserver.setMaxTotalConnections(100);
            //httpserver.setFollowRedirects(false);  // defaults to false
            // allowCompression defaults to false.
            // Server side must support gzip or deflate for this to have any effect.
            //httpserver.setAllowCompression(true);
            server = httpserver;
            results = SolrHelper.executeQuery(server);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Assume.assumeNotNull(results);

    }

    @Test
    public void testActivitiesCore() throws SolrServerException {
        SolrDocumentList results = SolrHelper.executeQuery(server);
        if (results == null) {
            Assert.fail("Exception occurred.");
        }
    }
}
