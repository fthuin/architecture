# Name of the output
set output "gnuplot.png"
set terminal png truecolor
set size 1.0,1.0
set xlabel "Size of matrices"
set ylabel "Average time"
# Activate the grid
set grid
plot 'simpleclient_result.csv' using 1:2:(0.00001) title 'average network time' smooth csplines, \
'' u 1:3:(0.00001) t 'average calculation time' smooth csplines, \
'' u 1:($2+$3):(0.00001) t 'average response time' smooth csplines
#plot 'output2' using 1:2:(0.00001) smooth unique
