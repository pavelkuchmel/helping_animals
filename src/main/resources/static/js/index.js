var btnAdminAnimalSearch = document.querySelector('#btn_admin-animal-search');
var btnAdminAnimalsSearchClose =  document.querySelector('#admin-animals-search-button_close');

btnAdminAnimalSearch.addEventListener('click', evt => {
        document.querySelector('.admin-animals-search-container').style.display = 'flex';
});
btnAdminAnimalsSearchClose.addEventListener('click', evt => {
    if (document.querySelector('.admin-animals-search-container').style.display == 'flex'){
        document.querySelector('.admin-animals-search-container').style.display = 'none';
    }
});