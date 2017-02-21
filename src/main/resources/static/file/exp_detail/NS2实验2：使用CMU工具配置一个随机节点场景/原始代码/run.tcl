set seed [exec ./RandomNumber] ;#get a random number as seed
puts "seed is $seed" 
puts "creating 50 random nodes"

exec ________________
#creat 50 random nodes by setdest, output to file RandomDest.txt; The route depends on different computers.

puts "creating nodes done"
puts "creating random cbr stream"

exec _________________
#creat 50 nodes' random cbr stream by cbrgen.tcl, output to file RandomCbr.txt; The route depends on different computers.

puts "creating cbr stream done\n"
puts "-------------Simulation-------------"

source RandomScene.tcl ;#run simulatior
