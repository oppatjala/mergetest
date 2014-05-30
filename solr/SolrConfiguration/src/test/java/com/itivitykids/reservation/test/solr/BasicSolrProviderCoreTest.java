/*
 * Copyright (c) ITivityKids, Inc. All Rights Reserved.
 */
package com.itivitykids.reservation.test.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrResourceLoader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.itivitykids.reservation.test.solr.util.SolrHelper;

/**
 * Test solr configuration.
  *
 * @author ovidio
 */
public class BasicSolrProviderCoreTest {

    public static EmbeddedSolrServer server;

    @BeforeClass
    public static void setup() {
        String homePath = "../.";
        SolrResourceLoader loader = new SolrResourceLoader(homePath);
        CoreContainer container = new CoreContainer(loader);
        container.load();

        server = new EmbeddedSolrServer(container, "providers");
    }

    @Test
    public void testActivitiesCore() throws SolrServerException {
        System.out.println("Core:" + server.getCoreContainer().getDefaultCoreName());
        SolrDocumentList results = SolrHelper.executeQuery(server);
        if (results == null) {
            Assert.fail("Exception occurred.");
        }

    }
}
