document.getElementById('eliminarBtn').addEventListener('click', function() {
    // Obtener los checkboxes seleccionados
    var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    var ids = [];
    for (var i = 0; i < checkboxes.length; i++) {
        var id = checkboxes[i].value; // Usar el atributo "value" en lugar del atributo "id"
        ids.push(id);
    }
    
    // Enviar los IDs al servidor para eliminar los registros
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
            var row = document.getElementById(`fila_${id}`); // Usar un atributo "id" único en las filas
            if (row) {
                row.parentNode.removeChild(row);
            }
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });
});