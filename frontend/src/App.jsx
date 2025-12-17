import React, { useState, useEffect } from "react";

function App() {
  const [tasks, setTasks] = useState([]);
  const [newTitle, setNewTitle] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchTasks();
  }, []);

  function fetchTasks() {
    fetch("http://localhost:8080/tasks")
      .then((res) => res.json())
      .then(setTasks)
      .catch(() => setTasks([]));
  }

  function handleCreateTask(e) {
    e.preventDefault();
    if (!newTitle.trim()) return;
    setLoading(true);
    fetch("http://localhost:8080/tasks", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ title: newTitle }),
    })
      .then((res) => {
        setNewTitle("");
        setLoading(false);
        fetchTasks();
      });
  }

  function handleMarkComplete(id) {
    fetch(`http://localhost:8080/tasks/${id}/complete`, {
      method: "PUT"
    })
      .then(fetchTasks);
  }

  return (
    <div style={{ maxWidth: 400, margin: "2rem auto", fontFamily: "sans-serif" }}>
      <h2>Tasks</h2>
      <form onSubmit={handleCreateTask} style={{ marginBottom: "1rem" }}>
        <input
          value={newTitle}
          onChange={e => setNewTitle(e.target.value)}
          placeholder="New Task"
          disabled={loading}
          style={{ width: "70%", marginRight: 10 }}
        />
        <button type="submit" disabled={loading}>
          Add
        </button>
      </form>
      <ul>
        {tasks.length === 0 ? (
          <li>No tasks</li>
        ) : (
          tasks.map(task => (
            <li key={task.id} style={{ marginBottom: 8 }}>
              <span style={{ textDecoration: task.completed ? "line-through" : "" }}>
                {task.title}
              </span>
              {!task.completed && (
                <button
                  onClick={() => handleMarkComplete(task.id)}
                  style={{ marginLeft: 10 }}
                >
                  Mark Complete
                </button>
              )}
            </li>
          ))
        )}
      </ul>
    </div>
  );
}

export default App;
