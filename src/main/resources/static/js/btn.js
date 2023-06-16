document.getElementById('eliminarBtn').addEventListener('click', function() {
    // Obtener los checkboxes seleccionados
    var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    var ids = [];
    for (var i = 0; i < checkboxes.length; i++) {
        var id = checkboxes[i].id.replace('checkbox_', '');
        ids.push(id);
    }
    
    // Enviar los IDs al servidor para eliminar los registros
    
    // Ejemplo de código para enviar una solicitud AJAX utilizando fetch
    fetch('/eliminarAlumnos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(ids)
    })
    .then(response => response.json())
    .then(data => {
        // Eliminar las filas correspondientes a los registros eliminados
        data.forEach(id => {
            var row = document.getElementById(`checkbox_${id}`).closest('tr');
            row.parentNode.removeChild(row);
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });
});
