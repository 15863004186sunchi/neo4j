/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.store;

import java.io.File;
import org.neo4j.io.pagecache.PageCache;
import org.neo4j.kernel.configuration.Config;
import org.neo4j.kernel.impl.store.format.RecordFormat;
import org.neo4j.kernel.impl.store.id.IdGeneratorFactory;
import org.neo4j.kernel.impl.store.id.IdType;
import org.neo4j.kernel.impl.store.record.RelationshipRecord;
import org.neo4j.logging.LogProvider;

import static org.neo4j.kernel.impl.store.NoStoreHeaderFormat.NO_STORE_HEADER_FORMAT;

/**
 * Implementation of the relationship store.
 */
public class RelationshipStore extends ComposableRecordStore<RelationshipRecord,NoStoreHeader>
{
    public static final String TYPE_DESCRIPTOR = "RelationshipStore";

    public RelationshipStore(
            File fileName,
            Config configuration,
            IdGeneratorFactory idGeneratorFactory,
            PageCache pageCache,
            LogProvider logProvider,
            RecordFormat<RelationshipRecord> recordFormat,
            String storeVersion )
    {
        super( fileName, configuration, IdType.RELATIONSHIP, idGeneratorFactory,
                pageCache, logProvider, TYPE_DESCRIPTOR, recordFormat, storeVersion, NO_STORE_HEADER_FORMAT );
    }

    @Override
    public <FAILURE extends Exception> void accept( Processor<FAILURE> processor, RelationshipRecord record )
            throws FAILURE
    {
        processor.processRelationship( this, record );
    }
}