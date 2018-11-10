package com.simon.credit.toolkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.simon.credit.toolkit.collection.CollectionToolkits;
import com.simon.credit.toolkit.hash.consistent.ServerNode;
import com.simon.credit.toolkit.logger.FormatLoggerFactory;
import com.simon.credit.toolkit.reflect.DataFetcher;
import com.simon.credit.toolkit.reflect.NotNullDataFetcher;
import com.simon.credit.toolkit.reflect.TypeRef;

public class CollectionTest {
	private static final Logger LOGGER = FormatLoggerFactory.getLogger(CollectionTest.class);

	public static void main(String[] args) {
		List<ServerNode> list = new ArrayList<ServerNode>();
		list.add(new ServerNode(null, 12345));
		list.add(new ServerNode("bbb", 12346));
		list.add(new ServerNode("ccc", 12347));
		list.add(new ServerNode("ddd", 12348));
		list.add(new ServerNode("ddd", 12348));
		list.add(new ServerNode("ddd", 12350));

		Set<String> coll = CollectionToolkits.collect(list, new NotNullDataFetcher<ServerNode, String>() {
			@Override
			public String fetch(ServerNode source) {
				return source.getServerNodeName();
			}
		}, new TypeRef<Set<String>>() {});

		LOGGER.info("=== className: {}", coll.getClass().getName());
		LOGGER.info("=== collection: {}", JSON.toJSONString(coll));

		Map<String, Collection<ServerNode>> map = CollectionToolkits.groupBy(list, new DataFetcher<ServerNode, String>() {
			@Override
			public String fetch(ServerNode source) {
				return source.getServerNodeName();
			}
		});
		LOGGER.info("=== map: {}", map);
	}

}
