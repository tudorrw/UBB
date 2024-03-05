#will be classes
class Auto:
    def __init__(self, marke, baujahr, farbe):
        self.marke = marke
        self.baujahr = baujahr
        self.farbe = farbe
    
    def __repr__(self):
        return 'Auto {}, gebaut in dem Jahr {}, hat die Farbe {}'.format(self.marke, self.baujahr, self.farbe)

    
