import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
<<<<<<< HEAD
import { getAuth, createUserWithEmailAndPassword, onAuthStateChanged , signInWithEmailAndPassword} from "firebase/auth"
=======
import { getAuth } from "firebase/auth"

>>>>>>> 42eeb8ff975fc61912ffd25e58d3c46ec48c3e69
const firebaseConfig = {
    apiKey: "AIzaSyBF6aowvCkRp_J2Xh7JETZliylDK7s1aGE",
    authDomain: "prateleira-inteligente.firebaseapp.com",
    projectId: "prateleira-inteligente",
    storageBucket: "prateleira-inteligente.firebasestorage.app",
    messagingSenderId: "394531712590",
    appId: "1:394531712590:web:d904fd79986954bd491d1f"
};

const app = initializeApp(firebaseConfig);


const db = getFirestore(app);

const auth = getAuth(app);

<<<<<<< HEAD
export { db, auth, createUserWithEmailAndPassword, onAuthStateChanged , signInWithEmailAndPassword};
=======
export { db, auth };
>>>>>>> 42eeb8ff975fc61912ffd25e58d3c46ec48c3e69
