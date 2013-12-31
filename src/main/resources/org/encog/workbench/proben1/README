  README file for PROBEN1 benchmark collection:
==================================================


  Contents of the PROBEN benchmark set directory:
 -------------------------------------------------

This directory contains datasets to be used for neural network training.
All data files use the same very simple format.
Each dataset is usually in its own subdirectory along its documentation.
The following directories exist:

building/
  Building enegery comsumption prediction problem.
  (Data of the "Great energy predictor shootout" contest)
  
cancer/
  Wisconsin breast cancer diagnosis problem from UCI machine learning database.

card/
  Credit card approval problem from UCI machine learning database.

diabetes/
  Diabetes diagnosis problem from UCI machine learning database

doc/
  Contains the techreport containing documentation of datasets and rules 
  and conventions for their use.

flare/
  solar flare prediction problem from UCI machine learning database

gene/
  gene splice-junction detection problem from UCI machine learning database

glass/
  Glass type identification problem from UCI machine learning database.

heart/
  heart-disease diagnosis problem from UCI machine learning database.

mushroom/
  mushroom edible/poisonous classification problem
  from UCI machine learning database.
  This directory is present only in the ADDENDUM to PROBEN1, since
  the mushroom problem is very large and not otherwise very interesting.

soybean/
  Soybean disease classification problem from UCI machine learning
  database.

thyroid/
  Thyroid normal/super/sub-function diagnosis problem from UCI machine
  learning database.

Scripts/
  Directory with some utility scripts for those who want to prepare their
  own datasets.
  Not needed when one only wants to use PROBEN1 without changing it.

========================================================================

Quick overview of the size of the datasets:

which   #attrib #in  #out #examples 
------------------------------------
building  6     14   3a   4208
cancer    9      9   2c    699
card     15     51   2c    690
diabetes  8      8   2c    768
flare     9     24   3a   1066
gene     60    120   3c   3175
glass     9      9   6c    214
heart    13     35   2c    920
heartc   13     35   2c    303
hearta   13     35   1a    920
heartac  13     35   1a    303
horse    20     58   3c    364
mushroom 22    125   2c   8124
soybean  35     82   19c   683
thyroid  21     21   3c   7200

 c = class outputs(0/1),  a = analog outputs(0...1)
 The heart, hearta, heartc, and heartac datasets
 are all in the heart directory (see there for documentation).

========================================================================

Description of data file format:

The following is what a data file looks like (example from glass1.dt):

bool_in=0
real_in=9
bool_out=6
real_out=0
training_examples=107
validation_examples=54
test_examples=53
0.281387 0.36391 0.804009 0.23676 0.643527 0.0917874 0.261152 0 0 1 0 0 0 0 0
0.260755 0.341353 0.772829 0.46729 0.545966 0.10628 0.255576 0 0 0 1 0 0 0 0
[further data lines deleted]

Each line after the header lines represents one example; first the
examples of the training set, then validation set, then test set.
The sizes of these sets are given in the last three header lines
(the partitioning is always 50%/25%/25% of the total number of examples).
The first four header lines describe the number of input coefficients and
output coefficients per example.
A boolean coefficient is always represented as either 0 (false) or 1 (true).
A real coefficient is represented as a decimal number between 0 and 1.
For all data sets, either bool_in or real_in is 0 and either bool_out or
real_out is 0.
Coefficients are separated by one or multiple spaces;
examples (including the last) are terminated by a single newline character.
That's all.

The datafiles of problem xx are named xx1.dt, xx2.dt, and xx3.dt;
they are located in directory proben1/xx.
The only difference between the three versions is the ordering
of the examples (so that different examples are in the training,
validation, and test set).

========================================================================

I suggest that you start by having a look at the techreport in the doc/
directory.

Most of the datasets are from the UCI machine learning databases archive
(available by anonymous ftp to ics.uci.edu [128.195.1.1] in
 directory /pub/machine-learning-databases).
This archive is maintained by Patrick M. Murphy and David W. Aha.
Many thanks to them for their valuable service.
The databases themselves were donated by various researchers -- see the
documentation files in the individual dataset directories for details.
Most data sets in the UCI repository are represented in symbolic form and are
meant to be used with symbolic machine learning algorithms.

What I have done is the following.
- I selected data sets that seemed suitable to neural network learning,
- For each of them, I decided on an attribute representation
- For each of them, I wrote a script to convert into this attribute
  representation, using an exactly identical target format for all data sets.
- I conducted some experiments with each of the data sets in order to
  find ball park figures of how good the results should be that one
  obtains when using the data sets.
- I wrote a report describing the data sets, the results, and a set of
  rules to be applied when using the data sets and when publishing results.
  
The goals of the whole project are
- to give NN researchers easier access to a number of data sets representing
  real problems.
- to make published results better reproducible.
- to make published results directly comparable.
- to decrease the frequency of methodological errors in NN benchmarking.

 Lutz

Lutz Prechelt (prechelt@ira.uka.de)
Department of Informatics
University of Karlsruhe
D-76128 Karlsruhe
Germany

========================================================================
