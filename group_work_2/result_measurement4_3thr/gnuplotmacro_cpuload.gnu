clear
reset
unset key
set yrange [0:400]
set output 'cpu_load.png'
set terminal png truecolor
set style data histogram
set style fill solid border
set style histogram clustered
set xlabel "Request rate"
set ylabel "Average CPU load (%)"
plot 'result.csv' using 2:xticlabels(1)
