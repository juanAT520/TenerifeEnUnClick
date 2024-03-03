package com.juan.tenerifeenunclick.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.juan.tenerifeenunclick.entity.Camino
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelRutas: ViewModel() {
    private val conexion = FirebaseFirestore.getInstance()
    private lateinit var listener: ListenerRegistration
    private var _listaRutas = MutableStateFlow(mutableStateListOf<Camino>())
    var listaRutas = _listaRutas.asStateFlow()
    private val _estaAbierto = MutableStateFlow(false)
    val estaAbierto = _estaAbierto.asStateFlow()
    private val _muestraMedidas = MutableStateFlow(false)
    val muestraMedidas = _muestraMedidas.asStateFlow()
    private val _nombreRuta = MutableStateFlow("")
    val nombreRuta = _nombreRuta.asStateFlow()
    private val _textoDesc = MutableStateFlow("Tenerife tiene multitud de rutas y senderos que abarcan todo el territorio de la isla, recorren desde los paisajes frondosos y verdes del Parque Rural de Anaga hasta otros más áridos en la zona sur.\n" +
            "\n" +
            "En esta sección podrás ver todas las rutas habilitadas para circular en bicicleta, caballo o vehículos a motor además de algunas curiosidades y lo que te puedes encontrar a lo largo de cada ruta.")
    val textoDesc = _textoDesc.asStateFlow()
    private val _mapaRecorrido = MutableStateFlow(listOf(LatLng(28.291692, -16.524134)))
    val mapaRecorrido = _mapaRecorrido.asStateFlow()
    private val _codigoRuta = MutableStateFlow("Selecciona una ruta")
    val codigoRuta = _codigoRuta.asStateFlow()
    private val _altitudRuta = MutableStateFlow(0.0)
    val altitudRuta = _altitudRuta.asStateFlow()
    private val _distanciaRuta = MutableStateFlow(0.0)
    val distanciaRuta = _distanciaRuta.asStateFlow()
    private val _listaRecorridos = addListaRecorridos()
    val listaRecorridos = _listaRecorridos.asStateFlow()
    fun crearListener() {
        listener = conexion.collection("Rutas").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambio ->
                    when (cambio.type) {
                        DocumentChange.Type.ADDED -> {
                            val camino = cambio.document.toObject<Camino>()
                            _listaRutas.value.add(camino)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val camino = cambio.document.toObject<Camino>()
                            val index = _listaRutas.value.indexOfFirst { it.Codigo == camino.Codigo }
                            if (index != -1) {
                                _listaRutas.value[index] = camino
                            }
                        }
                        DocumentChange.Type.REMOVED -> {
                            val camino = cambio.document.toObject<Camino>()
                            _listaRutas.value.remove(camino)
                        }
                    }
                }
            }
        }
    }


    fun borrarListener() {
        listener.remove()
    }

    fun AbreCierraDropDown() {
        _estaAbierto.value = !_estaAbierto.value
    }

    fun MuestraMedidas() {
        _muestraMedidas.value = true
    }

    fun CambiaRuta(nombre: String, descripcion: String, ruta: List<LatLng>, altitud: Double, distancia: Double, codigo: String) {
        _nombreRuta.value = nombre
        _textoDesc.value = descripcion
        _mapaRecorrido.value = ruta
        _altitudRuta.value = altitud
        _distanciaRuta.value = distancia
        _codigoRuta.value = codigo
    }

    private fun addListaRecorridos(): MutableStateFlow<List<List<LatLng>>> {
        val listaRecorridos = listOf(
            listOf(
                LatLng(28.413847400277, -16.406070700187),
                LatLng(28.404515, -16.395692),
                LatLng(28.411366, -16.391002),
                LatLng(28.403815, -16.390097),
                LatLng(28.411620, -16.387871),
                LatLng(28.411620, -16.387871),
                LatLng(28.408836, -16.386252),
                LatLng(28.408793000328, -16.384509400186)
            ),
            listOf(
                LatLng(28.177218, -16.645045),
                LatLng(28.183672, -16.634971),
                LatLng(28.177942, -16.634034),
                LatLng(28.178246, -16.628337),
                LatLng(28.187564, -16.624399),
                LatLng(28.178937, -16.615223),
                LatLng(28.170648, -16.610280),
                LatLng(28.169405, -16.620301),
                LatLng(28.173349, -16.620346),
                LatLng(28.172652, -16.625477),
                LatLng(28.167079, -16.629073),
                LatLng(28.171968, -16.632005),
                LatLng(28.165258, -16.634383)
            ),
            listOf(
                LatLng(28.439015, -16.429596),
                LatLng(28.437860, -16.428679),
                LatLng(28.439638, -16.428075),
                LatLng(28.43906344, -16.42604047),
                LatLng(28.43459098, -16.42217767),
                LatLng(28.43614057, -16.42072276)
            ),
            listOf(
                LatLng(28.44700933, -16.41182180),
                LatLng(28.44330675, -16.41742039),
                LatLng(28.44524047, -16.42002218),
                LatLng(28.44253994, -16.41966017),
                LatLng(28.43811592, -16.42289023),
                LatLng(28.43899766, -16.42466025)
            ),
            listOf(
                LatLng(28.459777, -16.400823),
                LatLng(28.455459, -16.405261),
                LatLng(28.448626, -16.403794)
            ),
            listOf(
                LatLng(28.361932, -16.604802),
                LatLng(28.362128, -16.601296),
                LatLng(28.361234, -16.599222),
                LatLng(28.355063, -16.602784),
                LatLng(28.353190, -16.602130),
                LatLng(28.354124, -16.603786),
                LatLng(28.351931, -16.608737),
                LatLng(28.349178, -16.608799),
                LatLng(28.349479, -16.605171),
                LatLng(28.345000, -16.607525)
            ),
            listOf(
                LatLng(28.428813, -16.376391),
                LatLng(28.436791, -16.382909),
                LatLng(28.437012, -16.386061),
                LatLng(28.446606, -16.383877),
                LatLng(28.443960, -16.386159),
                LatLng(28.447581, -16.389678),
                LatLng(28.440652, -16.394859),
                LatLng(28.442166, -16.397864),
                LatLng(28.446732, -16.393942),
                LatLng(28.447237, -16.404584),
                LatLng(28.447702, -16.404840),
                LatLng(28.432557, -16.425112),
                LatLng(28.429355, -16.425776),
                LatLng(28.429990, -16.422223),
                LatLng(28.409755, -16.435897),
                LatLng(28.409074, -16.439651),
                LatLng(28.395683, -16.454467),
                LatLng(28.392324, -16.452600),
                LatLng(28.389879, -16.454724),
                LatLng(28.393600, -16.458239),
                LatLng(28.392953, -16.460112),
                LatLng(28.389632, -16.476642),
                LatLng(28.385118, -16.475640),
                LatLng(28.380771, -16.479094),
                LatLng(28.361782, -16.488418),
                LatLng(28.361239, -16.487017),
                LatLng(28.354094, -16.494831),
                LatLng(28.359994, -16.503301),
                LatLng(28.343359, -16.585453),
                LatLng(28.360766, -16.598935),
                LatLng(28.353211, -16.602149),
                LatLng(28.351229, -16.629121),
                LatLng(28.345475, -16.654016),
                LatLng(28.339976, -16.652875),
                LatLng(28.314308, -16.708765),
                LatLng(28.302666, -16.732257),
                LatLng(28.268128, -16.746257)
            ),
            listOf(
                LatLng(28.320114, -16.756908),
                LatLng(28.318779, -16.754892),
                LatLng(28.315740, -16.758538),
                LatLng(28.315486, -16.746268),
                LatLng(28.319913, -16.741044),
                LatLng(28.318288, -16.721659),
                LatLng(28.317304, -16.702734)
            ),
            listOf(
                LatLng(28.332582, -16.758434),
                LatLng(28.318874, -16.754937),
                LatLng(28.315736, -16.758542),
                LatLng(28.314458, -16.747762),
                LatLng(28.309010, -16.748415),
                LatLng(28.309468, -16.746299),
                LatLng(28.303438, -16.744570),
                LatLng(28.306182, -16.733704),
                LatLng(28.302633, -16.732287),
                LatLng(28.284757, -16.748829),
                LatLng(28.268059, -16.745931)
            ),
            listOf(
                LatLng(28.356711, -16.502304),
                LatLng(28.352934, -16.496811),
                LatLng(28.346344, -16.499171),
                LatLng(28.347071, -16.515188),
                LatLng(28.342856, -16.514829),
                LatLng(28.336682, -16.527539),
                LatLng(28.329938, -16.532222)
            ),
            listOf(
                LatLng(28.359947, -16.504212),
                LatLng(28.360998, -16.502252),
                LatLng(28.362268, -16.504785),
                LatLng(28.359282, -16.525498),
                LatLng(28.356826, -16.530296),
                LatLng(28.356767, -16.523061)
            ),
            listOf(
                LatLng(28.347486, -16.515752),
                LatLng(28.342191, -16.528335),
                LatLng(28.334775, -16.544942),
                LatLng(28.330711, -16.550431),
                LatLng(28.334972, -16.582067)
            ),
            listOf(
                LatLng(28.333989, -16.677000),
                LatLng(28.333489, -16.684239),
                LatLng(28.331430, -16.687801),
                LatLng(28.330847, -16.689510),
                LatLng(28.325413, -16.687722),
                LatLng(28.322681, -16.681630)
            ),
            listOf(
                LatLng(28.258394, -16.769778),
                LatLng(28.279243, -16.782748),
                LatLng(28.261168, -16.777637),
                LatLng(28.255401, -16.772766),
                LatLng(28.259832, -16.772606),
                LatLng(28.257511, -16.769412)
            ),
            listOf(
                LatLng(28.349587, -16.525542),
                LatLng(28.340995, -16.569040),
                LatLng(28.343922, -16.584419)
            ),
            listOf(
                LatLng(28.207009, -16.538754),
                LatLng(28.209329, -16.538309),
                LatLng(28.206803, -16.540638),
                LatLng(28.210380, -16.541129),
                LatLng(28.206720, -16.543352),
                LatLng(28.203964, -16.541045),
                LatLng(28.203180, -16.549773),
                LatLng(28.203176, -16.548417),
                LatLng(28.203933, -16.550381),
                LatLng(28.204008, -16.548331),
                LatLng(28.207781, -16.549919),
                LatLng(28.207705, -16.546819),
                LatLng(28.209505, -16.549257),
                LatLng(28.210978, -16.547118),
                LatLng(28.214226, -16.551850),
                LatLng(28.215822, -16.550507),
                LatLng(28.216662, -16.556675),
                LatLng(28.222455, -16.552615),
                LatLng(28.221230, -16.550694),
                LatLng(28.231895, -16.544150),
                LatLng(28.233713, -16.541655),
                LatLng(28.241711, -16.538720),
                LatLng(28.246398, -16.533563),
                LatLng(28.251279, -16.529038)
            ),
            listOf(
                LatLng(28.403755, -16.485167),
                LatLng(28.402600, -16.488819),
                LatLng(28.397738, -16.490646),
                LatLng(28.401499, -16.486865),
                LatLng(28.400758, -16.485251),
                LatLng(28.400936, -16.486798),
                LatLng(28.399910, -16.487407),
                LatLng(28.399577, -16.482374),
                LatLng(28.402999, -16.477991),
                LatLng(28.402338, -16.473350),
                LatLng(28.407871, -16.464796),
                LatLng(28.405506, -16.461993),
                LatLng(28.424349, -16.427430)
            ),
            listOf(
                LatLng(28.165236, -16.634339),
                LatLng(28.172012, -16.631941),
                LatLng(28.167123, -16.628288),
                LatLng(28.172644, -16.625331),
                LatLng(28.169157, -16.616491),
                LatLng(28.181310, -16.591240),
                LatLng(28.182337, -16.594539)
            ),
            listOf(
                LatLng(28.439108, -16.429718),
                LatLng(28.435588, -16.432915),
                LatLng(28.433276, -16.432503),
                LatLng(28.432773, -16.422792),
                LatLng(28.435115, -16.420841),
                LatLng(28.434655, -16.422315),
                LatLng(28.439253, -16.426276),
                LatLng(28.449041, -16.405676),
                LatLng(28.447721, -16.404814)
            ),
            listOf(
                LatLng(28.361161, -16.657921),
                LatLng(28.341199, -16.652792),
                LatLng(28.336736, -16.620417),
                LatLng(28.340101, -16.620862),
                LatLng(28.339413, -16.615011),
                LatLng(28.344109, -16.612432),
                LatLng(28.344446, -16.592373),
                LatLng(28.316117, -16.573838)
            ),
            listOf(
                LatLng(28.339999, -16.623931),
                LatLng(28.336726, -16.625217),
                LatLng(28.337516, -16.621109),
                LatLng(28.341391, -16.622514),
                LatLng(28.340017, -16.618161),
                LatLng(28.343548, -16.620275),
                LatLng(28.346245, -16.617287)
            ),
            listOf(
                LatLng(28.459699, -16.400857),
                LatLng(28.455531, -16.405271),
                LatLng(28.414250, -16.405793)
            ),
            listOf(
                LatLng(28.326695, -16.533406),
                LatLng(28.336839, -16.505678),
                LatLng(28.332164, -16.511138),
                LatLng(28.333155, -16.507283),
                LatLng(28.329038, -16.511277),
                LatLng(28.319985, -16.513782),
                LatLng(28.319412, -16.498488),
                LatLng(28.307920, -16.507496),
                LatLng(28.307708, -16.518960),
                LatLng(28.302873, -16.522091)
            ),
            listOf(
                LatLng(28.413294, -16.420613),
                LatLng(28.407939, -16.428351),
                LatLng(28.403700, -16.428453),
                LatLng(28.406022, -16.431979),
                LatLng(28.404044, -16.431672),
                LatLng(28.404943, -16.435453),
                LatLng(28.409319, -16.436076),
                LatLng(28.415731, -16.441985)
            ),
            listOf(
                LatLng(28.423422, -16.379818),
                LatLng(28.423554, -16.382618),
                LatLng(28.421527, -16.389102),
                LatLng(28.419343, -16.389909),
                LatLng(28.417075, -16.390775),
                LatLng(28.417735, -16.399937),
                LatLng(28.414016, -16.402665),
                LatLng(28.413796, -16.405984)
            ),
            listOf(
                LatLng(28.302495, -16.514416),
                LatLng(28.292694, -16.511875),
                LatLng(28.276689, -16.518229),
                LatLng(28.274017, -16.515633),
                LatLng(28.252706, -16.526773),
                LatLng(28.246411, -16.533640),
                LatLng(28.240272, -16.537057),
                LatLng(28.233654, -16.541591),
                LatLng(28.229864, -16.537256),
                LatLng(28.230884, -16.534708)
            ),
            listOf(
                LatLng(28.414284, -16.405673),
                LatLng(28.416191, -16.409928),
                LatLng(28.418776, -16.409759),
                LatLng(28.418975, -16.410701),
                LatLng(28.420036, -16.411078),
                LatLng(28.421858, -16.413923),
                LatLng(28.420582, -16.419934),
                LatLng(28.424029, -16.413603),
                LatLng(28.424261, -16.418294),
                LatLng(28.413065, -16.432793)
            ),
            listOf(
                LatLng(28.430323, -16.392892),
                LatLng(28.441758, -16.384589)
            ),
            listOf(
                LatLng(28.343870, -16.584476),
                LatLng(28.343284, -16.582436),
                LatLng(28.343628, -16.581776),
                LatLng(28.342697, -16.580475),
                LatLng(28.342950, -16.579797),
                LatLng(28.342950, -16.579797),
                LatLng(28.341923, -16.577151),
                LatLng(28.341020, -16.576052),
                LatLng(28.341160, -16.575520),
                LatLng(28.340396, -16.576205),
                LatLng(28.341004, -16.580194),
                LatLng(28.341196, -16.582063),
                LatLng(28.339912, -16.581151),
                LatLng(28.337871, -16.577785),
                LatLng(28.337985, -16.583739),
                LatLng(28.336503, -16.583325),
                LatLng(28.337973, -16.586328),
                LatLng(28.334282, -16.584412),
                LatLng(28.334498, -16.581513),
                LatLng(28.333382, -16.579610),
                LatLng(28.332071, -16.579079),
                LatLng(28.332162, -16.582639),
                LatLng(28.330658, -16.579882),
                LatLng(28.330100, -16.583208),
                LatLng(28.332390, -16.585254),
                LatLng(28.331923, -16.585991)
            ),
            listOf(
                LatLng(28.42183800, -16.38692211),
                LatLng(28.41900560, -16.38628740),
                LatLng(28.41678934, -16.38070444),
                LatLng(28.42443096, -16.37210010),
                LatLng(28.42793553, -16.37253391)
            ),
            listOf(
                LatLng(28.448375, -16.378502),
                LatLng(28.446056, -16.382780),
                LatLng(28.446750, -16.383789),
                LatLng(28.438051, -16.391363),
                LatLng(28.438279, -16.392011),
                LatLng(28.437084, -16.397175),
                LatLng(28.432969, -16.407942),
                LatLng(28.431270, -16.411125),
                LatLng(28.430770, -16.421423),
                LatLng(28.430026, -16.425340),
                LatLng(28.429309, -16.425690)
            ),
            listOf(
                LatLng(28.341524, -16.653567),
                LatLng(28.341529, -16.658409),
                LatLng(28.338644, -16.658649),
                LatLng(28.338507, -16.665264),
                LatLng(28.335989, -16.670040),
                LatLng(28.337357, -16.670661),
                LatLng(28.333574, -16.674389),
                LatLng(28.332694, -16.680244),
                LatLng(28.327455, -16.688637),
                LatLng(28.328855, -16.696564),
                LatLng(28.326441, -16.709647),
                LatLng(28.326513, -16.709482),
                LatLng(28.325064, -16.719631),
                LatLng(28.330231, -16.732851),
                LatLng(28.329362, -16.736554),
                LatLng(28.334891, -16.744865),
                LatLng(28.333273, -16.749829),
                LatLng(28.335011, -16.752846),
                LatLng(28.332814, -16.757098)
            ),
            listOf(
                LatLng(28.329731, -16.532651),
                LatLng(28.331554, -16.537537),
                LatLng(28.335460, -16.535783),
                LatLng(28.336199, -16.536535),
                LatLng(28.332612, -16.540559),
                LatLng(28.334642, -16.542549),
                LatLng(28.334448, -16.544215),
                LatLng(28.334412, -16.544171),
                LatLng(28.336102, -16.545247),
                LatLng(28.335574, -16.577294),
                LatLng(28.336427, -16.577632),
                LatLng(28.337947, -16.583634),
                LatLng(28.337961, -16.577793),
                LatLng(28.338868, -16.579792),
                LatLng(28.340201, -16.580151),
                LatLng(28.339926, -16.581244),
                LatLng(28.341191, -16.582041),
                LatLng(28.340407, -16.580901),
                LatLng(28.341095, -16.575325)
            ),
            listOf(
                LatLng(28.354708, -16.631706),
                LatLng(28.352327, -16.632276),
                LatLng(28.350300, -16.629544),
                LatLng(28.349138, -16.630593),
                LatLng(28.351724, -16.633272),
                LatLng(28.347098, -16.629972),
                LatLng(28.347201, -16.629182),
                LatLng(28.346358, -16.630645),
                LatLng(28.345333, -16.629156),
                LatLng(28.345219, -16.627668),
                LatLng(28.342371, -16.624807),
                LatLng(28.339428, -16.624478),
                LatLng(28.336853, -16.625565),
                LatLng(28.337616, -16.627338)
            ),
            listOf(
                LatLng(28.394443, -16.482361),
                LatLng(28.394169, -16.484528),
                LatLng(28.392687, -16.481986),
                LatLng(28.390582, -16.482361),
                LatLng(28.394065, -16.486170),
                LatLng(28.395207, -16.485236),
                LatLng(28.395235, -16.482855),
                LatLng(28.396830, -16.481728),
                LatLng(28.394216, -16.477555),
                LatLng(28.395018, -16.475935),
                LatLng(28.393461, -16.477769),
                LatLng(28.394018, -16.479121),
                LatLng(28.393678, -16.479658),
                LatLng(28.391894, -16.478649),
                LatLng(28.391271, -16.479035),
                LatLng(28.391592, -16.480419),
                LatLng(28.390158, -16.479700),
                LatLng(28.389582, -16.476611)
            )
        )
        return MutableStateFlow(listaRecorridos)
    }
}