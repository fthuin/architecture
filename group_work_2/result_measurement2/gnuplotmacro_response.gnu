clear
reset
unset key
set yrange [1:200]
set output 'responsetime.png'
set terminal png truecolor
set style data histogram
set style fill solid border
set style histogram clustered
set logscale y
set xlabel "Request rate"
set ylabel "Average response time (seconds)"
plot 'result.csv' using 4:xticlabels(1)
