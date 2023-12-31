
const modalSearch = document.querySelector('#modal-Search'),
    closeSearch = document.querySelector('#close-Search'),
    btnMap = document.querySelector('#Map-button'),
    modalInnerSearch = document.querySelector('modal_inner-Search');

const modalFavorite = document.querySelector('#modal-Favorite'),
    closeFavorite = document.querySelector('#close-Favorite'),
    btnFavorite = document.querySelector('#Favorite-button'),
    // heartIcon = document.querySelector('.heart-icon'),
    modalInnerFavorite = document.querySelector('modal_inner-Favorite');



window.onload = () => {
    let e = gsap.timeline();
    e.to(btnMap,{
        display:"none"
    })
    e.to(modalSearch,{
        display:"flex"
    })
    e.from(modalSearch,{
        duration: 0.4,
        backgroundColor: "rgba(0, 0, 0, 0)",
        ease:"expo.out"
    },0.4)
    // e.from(modalInnerSearch,{
    //     duration:0.5,
    //     opacity: 0,
    //     ease: "power4.out"
    // })
    e.reverse();
    btnMap.onclick =() => {
        e.restart();
        closeSearch.onclick = () => {
            e.reverse();
        }
    }

    let k = gsap.timeline();
    k.to(btnFavorite,{
        display:"none"
    })
    k.to(modalFavorite,{
        display:"flex"
    })
    k.from(modalFavorite,{
        duration: 0.4,
        backgroundColor: "rgba(0, 0, 0, 0)",
        ease:"expo.out"
    },0.4)
    // k.from(modalInnerFavorite,{
    //     duration:0.5,
    //     opacity: 0,
    //     ease: "power4.out"
    // },1.3)
    k.reverse();
    btnFavorite.onclick =() => {
        k.restart();
        closeFavorite.onclick = () => {
            k.reverse();
        }
    }


}


// var heartIcon = document.getElementById("heart-icon");
// function likeToggle(provinceId){
//     // if(heartIcon.style.color =="grey") {heartIcon.style.color ="#d9317a"}
//     // else heartIcon.style.color ="grey";
//     if (typeof(Storage) != 'undefined'){
//         let favoriteList = JSON.parse(localStorage.getItem('favoriteList')) || [];

//         const isFavorite = favoriteList.includes(provinceId);

//         if (!isFavorite){
//             favoriteList.push(provinceId);

//             localStorage.setItem('favoriteList', JSON , stringify(favoriteList));
//             alert(`${provinceId} added to favorite list!`);
//         }
//         else{
//             favoriteList = favoriteList.filter(iten => item !== provinceId);
//             localStorage.setItem('favoriteList', JSON , stringify(favoriteList));
//             alert(`${provinceId} removed from favorite list!`);

//         }
//     }
//     else{
//         alert('Sorry, your browser does not support local storage.');
//     }

    
// }











