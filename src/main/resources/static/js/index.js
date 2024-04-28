document.querySelector('#btn_admin-page-search_animals').addEventListener('click', evt => {
    document.querySelector('#admin-page-search-container_animals').style.display = 'flex';
});
document.querySelector('#btn_admin-page-search_users').addEventListener('click', evt => {
    document.querySelector('#admin-page-search-container_users').style.display = 'flex';
});

function hideForm(event){
    event.parentElement.parentElement.style.display = 'none';
}
function hideError(event){
    event.parentElement.style.display = 'none';
}
function showConfirm(event){
    event.parentElement.childNodes.forEach(function (item){
        if (item.className === 'container-confirm'){
            item.style.display = 'block'
        }
    })
}