today = Date.today
two_days_ago = Date.today - 2.days
three_days_ago = Date.today - 3.days

TodoItem.destroy_all

TodoItem.create! [
  { title: "Task 1", due_date: today, description: "very important task TEST", completed: false },
  { title: "Task 2", due_date: two_days_ago, description: "learn ruby TEST", completed: true},
  { title: "Task 3", due_date: three_days_ago, description: "learn Active Record TEST", completed: true}
]
