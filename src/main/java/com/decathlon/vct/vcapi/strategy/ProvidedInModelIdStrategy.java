/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Original Work: Apache License, Version 2.0, Copyright 2017 Hans-Peter Grahsl.
 */

package com.decathlon.vct.vcapi.strategy;

import com.mongodb.kafka.connect.sink.converter.SinkDocument;
import com.mongodb.kafka.connect.sink.processor.id.strategy.IdStrategy;
import org.apache.kafka.connect.errors.DataException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.bson.BsonDocument;
import org.bson.BsonNull;
import org.bson.BsonValue;

import java.util.Optional;

abstract class ProvidedInModelIdStrategy implements IdStrategy {


    @Override
    public BsonValue generateId(final SinkDocument doc, final SinkRecord orig) {

        Optional<BsonDocument> valueDoc = doc.getValueDoc();

        if (valueDoc.isPresent()) {
            BsonValue id = valueDoc.get().get("model_id");
            if (id instanceof BsonNull) {
                throw new DataException(
                        "Provided id strategy used but the document structure contained an _id of type BsonNull");
            }
            return id;
        }

        throw new DataException(
                "Provided id strategy is used but the document structure either contained"
                        + " no _id field or it was null");
    }
}
