This is the task from the Java Programming course at the Jagiellonian University.

It was about implementing two dimensional map.
It can be viewed as rows and cells sheet, with row keys and column keys.
Parameters: <R> type of row keys, <C> type of column keys, <V> type of values

Methods specification:
- V put(R rowKey, C columnKey, V value): puts a value to the map, at given row and columns keys. If specified
row-column key already contains element, it should be replaced. It returns object that was previously contained by map within these coordinates, or null it it is empty.
Throws NullPointerException if rowKey or columnKey are null.
- V get(R rowKey, C columnKey): gets a value from the map from given key. It returns object cointained at specified key, or null, if key does not contain an object.
- V getOrDefault(R rowKey, C columnKey, V defaultValue): gets a value from the map from given key. If specified value does not exist, returns defaultValue.
- V remove(R rowKey, C columnKey): removes a value from the map from given key. It returns object contained at specified key, or null, if the key did not contain an object.
- boolean isEmpty(): checks if map contains no elements.
- boolean nonEmpty(): checks if map contains any element.
- int size(): returns number of values being stored by this map.
- void clear(): removes all objects from a map.
- Map<C, V> rowView(R rowKey): returns a view of mappings for specified key. Result map should be immutable. Later changes to this map should not affect result map. 
It returns map with view of particular row. If there is no values associated with specified row, empty map is returned.
- Map<R, V> columnView(C columnKey): returns a view of mappings for specified column. Result map should be immutable. Later changes to this map should not affect result map. It returns
map with view of particular column. If there is no values associated with specified column, empty map is returned.
- boolean hasValue(V value): checks if map contains specified value.
- boolean hasKey(R rowKey, C columnKey): checks if map contains a value under specified key.
- boolean hasRow(R rowKey): checks if map contains at least one valu within specified row.
- boolean hasColumn(C columnKey): checks if map contains at least one value within specified column.
- Map<R, Map<C,V>> rowMapView(): returns a view of this map as map of rows to map of columns to values. Result map should be immutable. Later changes to this map should not affect returned map.
It returns map with row-based view of this map. If this map is empty, empty map should be returned.
- Map<C, Map<R,V>> columnMapView():returns a view of this map as map of columns to map of rows to values. Result map should not affect returned map.
-  Map2D<R, C, V> fillMapFromRow(Map<? super C, ? super V> target, R rowKey): fills target map with all key-value maps from specified row.
Parameters: target map to be filled, rowKey row key to get data to fill map from. It returns this map.
-  Map2D<R, C, V> fillMapFromColumn(Map<? super R, ? super V> target, C columnKey): fills target map with all key-value maps from specified row.
Parameters: target to be filled, columnKey column key to get data to fill map from. It returns this map.
-  Map2D<R, C, V>  putAll(Map2D<? extends R, ? extends C, ? extends V> source): puts all content of source to this map. It returns this map.
- Map2D<R, C, V>  putAllToRow(Map<? extends C, ? extends V> source, R rowKey): puts all content of source map to this map under specified row.
 Eachkey of source map becomes a column part of key. It returns this map.
- Map2D<R, C, V>  putAllToColumn(Map<? extends R, ? extends V> source, C columnKey): puts all content of source map to this map under specified column. Each key of source map becomes a row part of key.
It returns this map.
- <R2, C2, V2> Map2D<R2, C2, V2> copyWithConversion(
          Function<? super R, ? extends R2> rowFunction,
          Function<? super C, ? extends C2> columnFunction,
          Function<? super V, ? extends V2> valueFunction): creates a copy of this map, with application of conversions for rows, columns and values to specified types.
If as result of row or column convertion result key duplicates, then appriopriate row and / or column in target map has to be merged.
If merge ends up in key duplication, then it's up to specific implementation which value from possible to choose. It returns new instance of Map2D with converted objects.
