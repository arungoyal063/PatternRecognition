#!/usr/bin/python
import sys

sys.path.append('/usr/local/lib/python3.5/dist-packages')

import json
import os
import plotly.offline as plotly


def process():
    if len(sys.argv) == 2:
        plot_data_file = str(sys.argv[1])
    else:
        sys.exit('expecting one arg path with data to be plotted')

    with open(plot_data_file, "r") as f:
        plot_data = json.loads(f.readline())

    f.close()

    plotly.plot(plot_data["figure"], filename=plot_data["filename"], auto_open=plot_data["auto_open"])

    os.remove(plot_data_file)
    sys.exit(0)


process()
