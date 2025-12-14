# Spring Boot Native Storage Module

In this project, we will build an extension to the RandomAccessFile class that
allows us to store and retrieve records. This “records file” will be equivalent
to a persistent hashtable, allowing keyed objects to be stored and retrieved 
from file storage. By extending a `RandomAccessFile` to support the storage 
and retrieval of arbitrary record data.

We are going to use a `RandomAccessFile` to provide a way of storing and 
retrieving record data. We’ll associate a key of type String with each record
as a means of uniquely identifying it. The keys will be limited to a maximum
length, although the record data will not be limited. For the purposes of this
example, our records will consist of only one field — a “blob” of binary data.
The file code will not attempt to interpret the record data in any way.



## References

- [Use a RandomAccessFile to build a low-level database](https://www.infoworld.com/article/2162952/use-a-randomaccessfile-to-build-a-low-level-database.html)
