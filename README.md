# XSpeedIt

Packing application that place items in fixed-size Packs.
Items are provided as a sequence of numbers, representing their weight, and result is a sequence of grouped items by Packs.

## Usage

XSpeedIt is a Java command-line application, packaged as a JAR.
A JRE in version `1.8` minimum is required to run it.
Possible commands are :

* run packing with the built-in sample `163841689525773` :
```
java -jar xspeedit.jar
```

* run packing on the provided [items], expected as a string of digits between 1 and 9
```
java -jar xspeedit.jar [items]
```

* run packing on 'count' generated samples, each containing 'items' items (both should integers > 0)
```
java -jar xspeedit.jar -g [count] -i [items]
```

* run packing on each line of the file found at 'path'
```
java -jar xspeedit.jar -f [path]
```

* display this usage information (any unrecognized command will display the usage)
```
java -jar xspeedit.jar --help
```


The result, with the number of Packs used and the packed sequence, is displayed on the console.
```
Packed using 8 pack(s) : 55/64/73/73/82/91/81/6
```

## Build

XSpeedIt is written in Scala.
Build environment required Scala `2.12` and SBT `0.13`.

Run the unit tests :
```
sbt test
```

Create a standalone (fat) JAR :
```
sbt assembly
mv target/scala-2.12/xspeedit-assembly-1.0.jar xspeedit.jar
```

## Design

The Packing algorithm logic is implemented in the [net.xpseedit.algo.PackBuilder] class.

It basic logic is :
* index items and result sequences by weight, to allow iteration by weight
* do a first split of items that can be grouped together, and that can't, in our case items >= 6, and create the first non-optimizable Packs
* iterate other remaining items, in descending weight order, to group and add them to the remaining Packs
Its bias is that larger items are taken first, which could lead to miss some optimizations on smaller items.

Each step uses Scala immutable collections operations, and the logic fits with a Map/Reduce approach.
This is less efficient than a loop/mutable collections approach on small sequences, but could allow to introduce partitionning and parallelism on large sequences, though this is not implemented here.

The automated tests are focused on :
* The algorithm's main property : it must pack all elements and not miss any. To ensure this Property-based testing is used (using `ScalaCheck`).
* The two main stages of the algorithm to ease refactoring
* Encode/Decode of the input/ouput data

