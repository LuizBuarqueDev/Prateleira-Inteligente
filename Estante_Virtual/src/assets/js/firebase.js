import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
import { getAuth, createUserWithEmailAndPassword, onAuthStateChanged } from "firebase/auth"
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

export { db, auth, createUserWithEmailAndPassword, onAuthStateChanged };