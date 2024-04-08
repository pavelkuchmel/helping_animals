document.querySelector('#btn_admin-page-search_animals').addEventListener('click', evt => {
    document.querySelector('#admin-page-search-container_animals').style.display = 'flex';
});
document.querySelector('#btn_admin-page-search_users').addEventListener('click', evt => {
    document.querySelector('#admin-page-search-container_users').style.display = 'flex';
});
// document.querySelector('#admin-page-search_animals_button_close').addEventListener('click', evt => {
//     if (document.querySelector('#admin-page-search-container_animals').style.display == 'flex'){
//         document.querySelector('#admin-page-search-container_animals').style.display = 'none';
//     }
// });
// document.querySelector('#admin-page-search_users_button_close').addEventListener('click', evt => {
//     if (document.querySelector('#admin-page-search-container_users').style.display == 'flex'){
//         document.querySelector('#admin-page-search-container_users').style.display = 'none';
//     }
// });
function hideForm(event){
    //console.log("1 - " + event.parentElement.className);
    //console.log("2 - " + event.parentElement.parentElement.className);
    //console.log("3 - " + event.parentElement.parentElement.parentElement.className)
    event.parentElement.parentElement.style.display = 'none';
}
