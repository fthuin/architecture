clear
reset
unset key
set yrange [0:300]
set output 'network_load.png'
set terminal png truecolor
set style data histogram
set style fill solid border
set style histogram clustered
set xlabel "Request rate"
set ylabel "Average network load (MB)"
plot 'result.csv' using 3:xticlabels(1)
