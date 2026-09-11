@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package fr.zoopedie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

private val Forest = Color(0xFF061A16)
private val Panel = Color(0xFF0C2923)
private val Green = Color(0xFF48B990)
private val Cream = Color(0xFFF0E9DA)

data class Animal(
    val id: String,
    val emoji: String,
    val name: String,
    val scientific: String,
    val category: String,
    val diet: String,
    val habitat: String,
    val range: String,
    val size: String,
    val weight: String,
    val lifespan: String,
    val conservation: String,
    val description: String
)

private val animals = listOf(
    Animal("lion", "🦁", "Lion", "Panthera leo", "Mammifère", "Carnivore", "Savane, prairie", "Afrique subsaharienne et Inde", "1,7 à 2,5 m", "120 à 250 kg", "10 à 14 ans", "Vulnérable", "Grand félin social vivant généralement en groupes appelés hardes."),
    Animal("elephant", "🐘", "Éléphant d'Afrique", "Loxodonta africana", "Mammifère", "Herbivore", "Savane, forêt", "Afrique subsaharienne", "Jusqu'à 7,5 m", "2 700 à 6 000 kg", "60 à 70 ans", "En danger", "Le plus grand animal terrestre vivant."),
    Animal("tiger", "🐅", "Tigre", "Panthera tigris", "Mammifère", "Carnivore", "Forêt, mangrove, prairie", "Asie", "2 à 3,3 m", "90 à 300 kg", "10 à 15 ans", "En danger", "Le plus grand des félins, connu pour son pelage rayé."),
    Animal("giraffe", "🦒", "Girafe", "Giraffa camelopardalis", "Mammifère", "Herbivore", "Savane", "Afrique", "4,5 à 5,7 m", "800 à 1 200 kg", "20 à 25 ans", "Vulnérable", "Animal terrestre le plus grand du monde."),
    Animal("penguin", "🐧", "Manchot empereur", "Aptenodytes forsteri", "Oiseau", "Carnivore", "Banquise", "Antarctique", "1,1 à 1,3 m", "22 à 45 kg", "15 à 20 ans", "Quasi menacé", "Le plus grand des manchots actuels."),
    Animal("croc", "🐊", "Crocodile du Nil", "Crocodylus niloticus", "Reptile", "Carnivore", "Rivières, lacs", "Afrique", "3 à 5 m", "225 à 750 kg", "70 à 100 ans", "Préoccupation mineure", "Grand prédateur aquatique à la mâchoire extrêmement puissante."),
    Animal("wolf", "🐺", "Loup gris", "Canis lupus", "Mammifère", "Carnivore", "Forêts, plaines, toundra", "Hémisphère Nord", "1 à 1,6 m", "20 à 80 kg", "6 à 13 ans", "Préoccupation mineure", "Prédateur social vivant généralement en meute."),
    Animal("gorilla", "🦍", "Gorille des plaines occidentales", "Gorilla gorilla gorilla", "Mammifère", "Herbivore", "Forêts tropicales", "Afrique centrale", "1,2 à 1,8 m", "68 à 180 kg", "35 à 50 ans", "En danger critique", "Le plus nombreux des gorilles, menacé par la chasse et la perte d'habitat."),
    Animal("panda", "🐼", "Panda géant", "Ailuropoda melanoleuca", "Mammifère", "Herbivore", "Forêts de bambous", "Chine", "1,2 à 1,9 m", "70 à 125 kg", "20 à 30 ans", "Vulnérable", "Grand mammifère connu pour son alimentation dominée par le bambou."),
    Animal("komodo", "🦎", "Dragon de Komodo", "Varanus komodoensis", "Reptile", "Carnivore", "Savanes sèches", "Indonésie", "2 à 3 m", "70 à 90 kg", "20 à 30 ans", "En danger", "Le plus grand lézard vivant au monde."),
    Animal("whale", "🐋", "Baleine bleue", "Balaenoptera musculus", "Mammifère", "Carnivore", "Océans", "Tous les grands océans", "24 à 30 m", "100 à 180 tonnes", "70 à 90 ans", "En danger", "Le plus grand animal connu ayant jamais vécu."),
    Animal("shark", "🦈", "Grand requin blanc", "Carcharodon carcharias", "Poisson", "Carnivore", "Océans tempérés", "Océans du monde", "3,5 à 6 m", "500 à 2 000 kg", "30 à 70 ans", "Vulnérable", "Grand prédateur marin au rôle écologique majeur."),
    Animal("eagle", "🦅", "Aigle royal", "Aquila chrysaetos", "Oiseau", "Carnivore", "Montagnes et espaces ouverts", "Hémisphère Nord", "1,8 à 2,3 m d'envergure", "3 à 6,5 kg", "20 à 30 ans", "Préoccupation mineure", "Rapace puissant doté d'une vue exceptionnelle."),
    Animal("macaw", "🦜", "Ara bleu et jaune", "Ara ararauna", "Oiseau", "Omnivore", "Forêts tropicales", "Amérique du Sud", "76 à 86 cm", "0,9 à 1,5 kg", "30 à 50 ans", "Préoccupation mineure", "Grand perroquet social aux couleurs éclatantes."),
    Animal("frog", "🐸", "Dendrobate doré", "Phyllobates terribilis", "Amphibien", "Insectivore", "Forêt tropicale humide", "Colombie", "4 à 5 cm", "1 g", "10 à 15 ans", "En danger", "Petite grenouille célèbre pour ses toxines cutanées."),
    Animal("tortoise", "🐢", "Tortue géante des Galápagos", "Chelonoidis niger", "Reptile", "Herbivore", "Zones sèches et herbeuses", "Îles Galápagos", "Jusqu'à 1,5 m", "250 kg", "100 ans et plus", "Vulnérable", "L'une des plus grandes tortues terrestres au monde."),
    Animal("zebra", "🦓", "Zèbre des plaines", "Equus quagga", "Mammifère", "Herbivore", "Savanes et prairies", "Afrique de l'Est et australe", "2,2 à 2,6 m", "175 à 385 kg", "20 à 30 ans", "Quasi menacé", "Équidé reconnaissable à son pelage rayé unique."),
    Animal("rhino", "🦏", "Rhinocéros blanc", "Ceratotherium simum", "Mammifère", "Herbivore", "Savane", "Afrique australe et orientale", "3,4 à 4,2 m", "1 700 à 2 300 kg", "40 à 50 ans", "Quasi menacé", "Grand herbivore dont la protection dépend fortement de la lutte contre le braconnage."),
    Animal("fox", "🦊", "Renard roux", "Vulpes vulpes", "Mammifère", "Omnivore", "Forêts, campagnes et villes", "Hémisphère Nord", "90 à 140 cm", "3 à 14 kg", "3 à 6 ans", "Préoccupation mineure", "Canidé très adaptable présent dans de nombreux milieux."),
    Animal("dolphin", "🐬", "Grand dauphin", "Tursiops truncatus", "Mammifère", "Carnivore", "Mers et océans côtiers", "Océans tempérés et tropicaux", "2 à 4 m", "150 à 650 kg", "20 à 50 ans", "Préoccupation mineure", "Cétacé social et très intelligent."),
    Animal("owl", "🦉", "Grand-duc d'Europe", "Bubo bubo", "Oiseau", "Carnivore", "Forêts et falaises", "Europe et Asie", "160 à 188 cm d'envergure", "1,5 à 4 kg", "20 à 25 ans", "Préoccupation mineure", "L'un des plus grands rapaces nocturnes du monde."),
    Animal("orangutan", "🦧", "Orang-outan de Bornéo", "Pongo pygmaeus", "Mammifère", "Omnivore", "Forêt tropicale", "Bornéo", "1,2 à 1,5 m", "30 à 100 kg", "35 à 45 ans", "En danger critique", "Grand singe arboricole gravement menacé par la destruction des forêts."),
    Animal("octopus", "🐙", "Poulpe commun", "Octopus vulgaris", "Invertébré", "Carnivore", "Fonds rocheux", "Mers tempérées et tropicales", "Jusqu'à 1,3 m", "3 à 10 kg", "1 à 3 ans", "Préoccupation mineure", "Céphalopode doté de capacités remarquables de camouflage et d'apprentissage."),
    Animal("butterfly", "🦋", "Monarque", "Danaus plexippus", "Invertébré", "Nectarivore", "Prairies et jardins", "Amériques", "9 à 12 cm d'envergure", "Moins de 1 g", "Quelques semaines", "En danger", "Papillon célèbre pour ses grandes migrations."),
    Animal("hippo", "🦛", "Hippopotame", "Hippopotamus amphibius", "Mammifère", "Herbivore", "Rivières et lacs", "Afrique subsaharienne", "3,3 à 5,2 m", "1 300 à 3 200 kg", "40 à 50 ans", "Vulnérable", "Grand mammifère semi-aquatique territorial."),
    Animal("cheetah", "🐆", "Guépard", "Acinonyx jubatus", "Mammifère", "Carnivore", "Savanes et zones ouvertes", "Afrique et petite zone d'Asie", "1,1 à 1,5 m", "21 à 72 kg", "10 à 15 ans", "Vulnérable", "Le plus rapide des animaux terrestres."),
    Animal("swan", "🦢", "Cygne tuberculé", "Cygnus olor", "Oiseau", "Herbivore", "Lacs et rivières", "Europe et Asie", "1,4 à 1,6 m", "8 à 14 kg", "20 à 30 ans", "Préoccupation mineure", "Grand oiseau aquatique reconnaissable à son long cou."),
    Animal("chameleon", "🦎", "Caméléon panthère", "Furcifer pardalis", "Reptile", "Insectivore", "Forêts et broussailles", "Madagascar", "35 à 55 cm", "100 à 220 g", "2 à 5 ans", "Préoccupation mineure", "Reptile arboricole capable de changements de couleur complexes.")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ZoopedieApp() }
    }
}

@Composable
fun ZoopedieApp() {
    var favorites by remember { mutableStateOf(setOf<String>()) }
    val nav = rememberNavController()
    MaterialTheme(colorScheme = darkColorScheme(primary = Green, background = Forest, surface = Panel, onSurface = Cream)) {
        NavHost(navController = nav, startDestination = "home", modifier = Modifier.background(Forest)) {
            composable("home") { HomeScreen(onExplore = { nav.navigate("animals") }, onFavorites = { nav.navigate("favorites") }, onAnimal = { nav.navigate("animal/$it") }, favorites = favorites, animals = animals) }
            composable("animals") { AnimalListScreen(animals, favorites, onBack = { nav.popBackStack() }, onAnimal = { nav.navigate("animal/$it") }) }
            composable("favorites") { AnimalListScreen(animals.filter { it.id in favorites }, favorites, title = "Mes favoris", onBack = { nav.popBackStack() }, onAnimal = { nav.navigate("animal/$it") }) }
            composable("animal/{id}", arguments = listOf(navArgument("id") { type = NavType.StringType })) { back ->
                val animal = animals.first { it.id == back.arguments?.getString("id") }
                AnimalDetailScreen(animal, animal.id in favorites, onBack = { nav.popBackStack() }, onToggleFavorite = {
                    favorites = if (animal.id in favorites) favorites - animal.id else favorites + animal.id
                })
            }
        }
    }
}

@Composable
private fun HomeScreen(onExplore: () -> Unit, onFavorites: () -> Unit, onAnimal: (String) -> Unit, favorites: Set<String>, animals: List<Animal>) {
    Scaffold(containerColor = Forest, bottomBar = { NavigationBar(containerColor = Panel) {
        NavigationBarItem(true, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Accueil") })
        NavigationBarItem(false, onClick = onExplore, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Explorer") })
        NavigationBarItem(false, onClick = onFavorites, icon = { Icon(Icons.Default.Star, null) }, label = { Text("Favoris") })
    } }) { padding ->
        LazyColumn(contentPadding = PaddingValues(20.dp), modifier = Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { Text("ZOOPÉDIE", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = Cream); Text("DÉCOUVREZ LE MONDE ANIMAL", color = Green) }
            item { Button(onClick = onExplore, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Search, null); Spacer(Modifier.width(8.dp)); Text("Rechercher un animal") } }
            item { Card(colors = CardDefaults.cardColors(containerColor = Panel), modifier = Modifier.fillMaxWidth().clickable { onExplore() }) { Column(Modifier.padding(20.dp)) { Text("EXPLOREZ", color = Green, fontWeight = FontWeight.Bold); Text("Le monde animal", style = MaterialTheme.typography.headlineSmall); Text("Une encyclopédie immersive des espèces animales.", color = Cream.copy(alpha = .75f)) } } }
            item { Text("CATÉGORIES", fontWeight = FontWeight.Bold, color = Green) }
            item { Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) { listOf("🐘 Mammifères", "🦅 Oiseaux", "🦎 Reptiles").forEach { AssistChip(onClick = onExplore, label = { Text(it) }) } } }
            item { Text("À découvrir", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
            items(animals.take(4)) { AnimalRow(it, it.id in favorites) { onAnimal(it.id) } }
        }
    }
}

@Composable
private fun AnimalListScreen(animals: List<Animal>, favorites: Set<String>, title: String = "Explorer les animaux", onBack: () -> Unit, onAnimal: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Toutes") }
    val categories = listOf("Toutes") + animals.map { it.category }.distinct()
    val filtered = animals.filter { animal ->
        (category == "Toutes" || animal.category == category) &&
        (animal.name.contains(query, true) || animal.scientific.contains(query, true) || animal.category.contains(query, true) || animal.diet.contains(query, true))
    }
    Scaffold(containerColor = Forest, topBar = { TopAppBar(title = { Text(title) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(query, { query = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Rechercher un animal...") }, leadingIcon = { Icon(Icons.Default.Search, null) }, singleLine = true)
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                categories.take(5).forEach { item -> FilterChip(selected = category == item, onClick = { category = item }, label = { Text(item) }) }
            }
            Spacer(Modifier.height(8.dp))
            Text("${filtered.size} espèces", color = Green)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) { items(filtered) { AnimalRow(it, it.id in favorites) { onAnimal(it.id) } } }
        }
    }
}

@Composable
private fun AnimalRow(animal: Animal, favorite: Boolean, onClick: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(animal.emoji, style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) { Text(animal.name, fontWeight = FontWeight.Bold); Text(animal.scientific, fontStyle = FontStyle.Italic, color = Cream.copy(alpha = .7f)) }
            if (favorite) Icon(Icons.Default.Star, null, tint = Color(0xFFFFC857)) else Icon(Icons.Default.ChevronRight, null)
        }
    }
}

@Composable
private fun AnimalDetailScreen(animal: Animal, favorite: Boolean, onBack: () -> Unit, onToggleFavorite: () -> Unit) {
    Scaffold(containerColor = Forest, topBar = { TopAppBar(title = { Text(animal.name) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }, actions = { IconButton(onClick = onToggleFavorite) { Icon(if (favorite) Icons.Default.Star else Icons.Default.StarBorder, null, tint = if (favorite) Color(0xFFFFC857) else Cream) } }) }) { padding ->
        LazyColumn(Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Text(animal.emoji, style = MaterialTheme.typography.displayLarge); Text(animal.name, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black); Text(animal.scientific, fontStyle = FontStyle.Italic, color = Green) }
            item { InfoCard("INFORMATIONS") { Info("Classification", animal.category); Info("Régime alimentaire", animal.diet); Info("Habitat", animal.habitat); Info("Répartition", animal.range); Info("Taille", animal.size); Info("Poids", animal.weight); Info("Espérance de vie", animal.lifespan); Info("Conservation", animal.conservation) } }
            item { InfoCard("DESCRIPTION") { Text(animal.description) } }
            item { InfoCard("RÉPARTITION") { Text("Carte interactive à connecter à une source géographique dans la prochaine version.", color = Cream.copy(alpha = .75f)) } }
            item { InfoCard("BIOLOGIE") { Text("Cette section accueillera la reproduction, le comportement, la morphologie et les données scientifiques détaillées.", color = Cream.copy(alpha = .75f)) } }
        }
    }
}

@Composable private fun InfoCard(title: String, content: @Composable ColumnScope.() -> Unit) { Card(colors = CardDefaults.cardColors(containerColor = Panel), modifier = Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) { Text(title, color = Green, fontWeight = FontWeight.Bold); Spacer(Modifier.height(10.dp)); content() } } }
@Composable private fun Info(label: String, value: String) { Row(Modifier.fillMaxWidth().padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text(label, color = Cream.copy(alpha = .65f)); Text(value, modifier = Modifier.padding(start = 12.dp), color = Cream) } }
