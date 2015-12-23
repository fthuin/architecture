clear
reset
unset key
set yrange [0:315]
set output 'network_load.png'
set terminal png truecolor
set style data histogram
set style fill solid border
set style histogram clustered
set xlabel "Request rate"
set ylabel "Average network load (MB)"
plot for [COL=4:5] 'result3_4thr.csv' using COL:xticlabels(1)
