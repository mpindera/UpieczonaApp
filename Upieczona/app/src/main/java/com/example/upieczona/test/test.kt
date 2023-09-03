package com.example.upieczona.test

fun main() {
  val str = """
<p><strong>WYKONANIE CIASTA BANANOWEGO</strong><br>1. Banany przekładamy do sporej miski i rozgniatamy widelcem. Nie rozdrabniamy bananów na gładką papkę, wystarczy, że delikatnie jest rozgnieciemy. <br>2. Do rozgniecionych bananów dodajemy wszystkie pozostałe składniki, czyli jajka, mąkę ryżową, proszek budyniowy, proszek do pieczenia, sodę, sól, oliwę z oliwek oraz ocet jabłkowy.  Całość dokładnie mieszamy (najlepiej za pomocą trzepaczki). Powinnismy otrzymać dość gęste ciasto z grudkami bananów.<br>3. Tak przygotowana masę przekładamy do okrągłej blaszki wyłożonej papierem do pieczenia.<br>4. Wkładamy do piekarnika nagrzanego do 170 stopni i pieczemy przez około 40-50 minut lub do suchego patyczka. <br>5. Upieczona ciasto wyjmujemy z piekarnika i pozostawiamy do całkowitego wystudzenia. </p>
<p><strong>WYKONANIE MUSU CZEKOLADOWEGO</strong><br>1. W małym garnuszku umieszczamy 30g śmietanki kremówki, cukier oraz sól. całość podgrzewamy, cały czas mieszając do zagotowania. Kiedy zawartość rondelka zacznie delikatnie wrzeć, wyłączamy palnik i dodajemy posiekaną czekoladę. Całość mieszamy do momentu kiedy czekolada się rozpuści.<br>2. W oddzielnym wysokim naczyniu umieszczamy pozostałe 100g śmietanki kremówki a następnie ubijamy ją (za pomocą blendera z końcówką trzepaczki) na bardzo sztywną pianę. <br>3. Do ubitej śmietanki dodajemy zawartość garnuszka. Całość bardzo delikatnie mieszamy (najlepiej za pomocą silikonowej szpatułki) do połączenia obu mas. <br>4. Tak przygotowany mus przekładamy do wystudzone ciasto bananowe i wkładamy do lodówki. Mus po około godzinie w lodówce powinien być już bardzo sztywny. </p>
<p><strong>WYKONANIE ORZECHÓW WŁOSKICH W KARMELU</strong><br>1. Cukier, masło orzechowe, napój roślinny oraz sól umieszczamy w garnuszku i dokładnie mieszamy. Całość umieszczamy na średnim ogniu i podgrzewamy cały czas mieszając do zagotowania. Mieszamy i gotujemy na niskim ogniu do momentu kiedy całość porządnie zgęstnieje. Może potrwać to około 10 minut.&nbsp;<br>2. Do gęstej masy dodajemy orzechy włoskie i całość mieszamy do momentu kiedy karmel oblepi orzechy włoskie.<br>3. Orzechami dekorujemy wierzch ciasta.<br>4. Smacznego!</p>
""".trimIndent()
  println(
    fetchRecipe1(str)
  )
}

fun fetchRecipe1(input: String): List<String> {
  val pattern = "<p>.*?</p>".toRegex()
  val matches = pattern.findAll(input).map { it.value }.toList()

  val ap = matches.map { paragraph ->
    paragraph
      .replace("<br>", "\n   ").replace(Regex("<strong>.*?<\\/strong>"), "").replace("<p>","").replace("</p>","\n")
  }

  return ap
}

