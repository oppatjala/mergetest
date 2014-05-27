/*
 * Copyright (c) ITivityKids, Inc. All Rights Reserved.
 */
package com.itivitykids.reservation.test.solr.util;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;

/**
 * Common functionality for Solr servers.
 *
 * @author ovidio
 */
public class SolrHelper {

    public static SolrDocumentList executeQuery(SolrServer server) {
        SolrDocumentList results = null;
        try {
            SolrQuery query = new SolrQuery();
            query.set(CommonParams.QT, "/search");
            results = server.query(query).getResults();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (results != null) {
            System.out.println("Results found:" + results.getNumFound());
            Object[] o = results.toArray();
            for (int i = 0; i < o.length; i++) {
                SolrDocument doc = (SolrDocument) o[i];
                System.out.println(String.format("Results found[%d]:%s", i, doc));
            }           
        }

        return results;
    }

}
